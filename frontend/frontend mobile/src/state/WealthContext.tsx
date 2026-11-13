import React, { createContext, useContext, useEffect, useRef, useState } from 'react';
import { BackHandler, Platform, Pressable, ScrollView, Share, Text, View } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Ionicons } from '@expo/vector-icons';
import { Button, Card, Field, Choices } from '../components';
import { C, styles } from '../theme/styles';
import { screens } from '../navigation/routes';
import { initialStore, Store, totals, Transaction, project } from '../model';
import { money, id, today } from '../utils/format';
type Icon = React.ComponentProps<typeof Ionicons>['name'];
const KEY = 'wealth-e-mobile-v1';
export function useWealthController() {
 const [store, setStore] = useState<Store>(initialStore);
 const [ready, setReady] = useState(false);
 const [storageError, setStorageError] = useState('');
 const [page, setPage] = useState(0);
 const [history, setHistory] = useState<{ page: number; selected: string }[]>([]);
 const [menu, setMenu] = useState(false);
 const [query, setQuery] = useState('');
 const [filter, setFilter] = useState('Tous');
 const [showFilter, setShowFilter] = useState(false);
 const [selected, setSelected] = useState('');
 const [toast, setToast] = useState('');
 const [editor, setEditor] = useState(false);
 const [draft, setDraft] = useState<Record<string, string>>({});
 const [error, setError] = useState('');
 const [result, setResult] = useState('');
 const [csv, setCsv] = useState('date;libelle;montant;categorie\n2026-11-07;Courses;-45000;Alimentation');
 const [preview, setPreview] = useState<Transaction[]>([]);
 const [question, setQuestion] = useState('');
 const [messages, setMessages] = useState<{ question: string; answer: string }[]>([]);
 const [natural, setNatural] = useState<{ initial: number; monthly: number; months: number; rate: number } | null>(null);
 const scroll = useRef<React.ComponentRef<typeof ScrollView>>(null);
 const saveQueue = useRef(Promise.resolve());
 const t = totals(store);
 const fmt = (v: number) => money(v, store.profile.currency);
 const notify = (text: string) => setToast(text);
 useEffect(() => { let mounted = true; AsyncStorage.getItem(KEY).then(raw => { if (!mounted) return; if (raw) { const saved = JSON.parse(raw); if (!Array.isArray(saved.accounts) || !Array.isArray(saved.transactions) || !saved.profile) throw new Error('Données invalides'); setStore({ ...initialStore, ...saved }); } }).catch(() => { if (mounted) setStorageError('Impossible de lire la sauvegarde. Les données démo sont affichées.'); }).finally(() => { if (mounted) setReady(true); }); return () => { mounted = false; }; }, []);
 useEffect(() => { if (!ready) return; saveQueue.current = saveQueue.current.catch(() => {}).then(() => AsyncStorage.setItem(KEY, JSON.stringify(store))).catch(() => setStorageError('Sauvegarde indisponible : vos dernières modifications restent en mémoire.')); }, [store, ready]);
 useEffect(() => { if (!toast) return; const timer = setTimeout(() => setToast(''), 4500); return () => clearTimeout(timer); }, [toast]);
 const resetPageState = () => { setDraft({}); setError(''); setResult(''); setPreview([]); setFilter('Tous'); setQuery(''); setShowFilter(false); setEditor(false); setNatural(null); setQuestion(''); scroll.current?.scrollTo({ y: 0, animated: false }); };
 const go = (next: number, selection?: string) => { if (next < 0 || next >= screens.length) return; if (next !== page || selection !== undefined) setHistory(h => [...h, { page, selected }]); setPage(next); if (selection !== undefined) setSelected(selection); setMenu(false); resetPageState(); };
 const back = () => { if (menu) { setMenu(false); return true; } if (history.length) { const last = history[history.length - 1]; setHistory(h => h.slice(0, -1)); setPage(last.page); setSelected(last.selected); resetPageState(); return true; } return false; };
 useEffect(() => { const sub = BackHandler.addEventListener('hardwareBackPress', back); return () => sub.remove(); }, [history, menu]);
 const update = (fn: (s: Store) => Store) => setStore(s => ({ ...fn(s), updatedAt: new Date().toISOString() }));
 const change = (key: string) => (value: string) => { setError(''); setResult(''); setDraft(d => ({ ...d, [key]: value, ...(key === 'type' ? { category: value === 'Revenu' ? 'Revenu' : 'Alimentation' } : {}) })); };
 const amount = (key: string, zero = false) => { const raw = draft[key]?.replace(/\s/g, '').replace(',', '.'); const n = Number(raw); if (!raw || !Number.isFinite(n) || (zero ? n < 0 : n <= 0)) throw new Error('Saisissez un montant valide ' + (zero ? 'positif ou nul.' : 'supérieur à zéro.')); return n; };
 const run = (fn: () => void) => { try { setError(''); fn(); } catch (e) { setError(e instanceof Error ? e.message : 'Une erreur est survenue.'); } };
 const field = (key: string, label: string, numeric = false, secret = false) => <Field key={key} label={label} value={draft[key] || ''} onChangeText={change(key)} numeric={numeric} secret={secret} />;
 const pick = (key: string, values: string[], defaultValue = values[0]) => <Choices values={values} value={draft[key] || defaultValue} onChange={change(key)} />;
 const accountPicker = (key: string) => <><Text style={styles.label}>Compte {key === 'to' ? 'destinataire' : 'source'}</Text>{pick(key, store.accounts.map(a => a.name), key === 'to' ? store.accounts[1]?.name : store.accounts[0]?.name)}</>;
 const chosenAccount = (key: string, fallback = 0) => store.accounts.find(a => a.name === draft[key]) || store.accounts[fallback];
 const requiredName = () => { if (!draft.name?.trim()) throw new Error('Le nom / libellé est obligatoire.'); return draft.name.trim(); };
 const validDate = () => { const date = draft.date || today(); if (!/^\d{4}-\d{2}-\d{2}$/.test(date) || !Number.isFinite(Date.parse(date)) || new Date(date).toISOString().slice(0, 10) !== date) throw new Error('Date invalide. Format : AAAA-MM-JJ.'); return date; };
 const addTransactions = (rows: Transaction[]) => update(s => ({ ...s, transactions: [...rows, ...s.transactions], accounts: s.accounts.map(a => ({ ...a, balance: a.balance + rows.filter(r => r.account === a.id).reduce((sum, r) => sum + r.amount, 0) })) }));
 const exportRows = async (title: string, rows: string[][]) => { try { const content = ['Élément;Valeur;État', ...rows.map(row => row.map(v => `"${v.replace(/"/g, '""')}"`).join(';'))].join('\n'); if (Platform.OS === 'web') { const url = URL.createObjectURL(new Blob(['\uFEFF' + content], { type: 'text/csv;charset=utf-8' })); const a = document.createElement('a'); a.href = url; a.download = `wealth-e-${page + 1}.csv`; a.click(); URL.revokeObjectURL(url); } else { await Share.share({ title, message: title + '\n' + content }); } } catch { notify('Le partage n’a pas pu aboutir.'); } };
 const link = (label: string, next: number, icon: Icon = 'chevron-forward') => <Pressable accessibilityRole="button" key={label} onPress={() => go(next)} style={styles.link}><Text style={styles.linkText}>{label}</Text><Ionicons name={icon} size={17} color={C.teal} /></Pressable>;
 const info = <Card title="Informations"><Text style={styles.body}>Données de démonstration sauvegardées sur cet appareil.</Text><View style={styles.notice}><Text style={styles.noticeText}>Mode démonstration : aucune connexion bancaire réelle.</Text></View></Card>;
 const actions = (submit: () => void, title = 'Valider') => <><View style={styles.wrap}><Button title={title} onPress={() => run(submit)} /><Button title="Annuler" secondary onPress={() => { setDraft({}); setError(''); setEditor(false); if (!editor) back(); }} /></View>{!!error && <Text accessibilityRole="alert" style={styles.error}>{error}</Text>}{!!result && <View style={styles.notice}><Text style={styles.noticeText}>{result}</Text></View>}</>;

 function submitForm() {
  if ([1, 2, 14].includes(page)) {
   if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(draft.email || '')) throw new Error('Saisissez une adresse e-mail valide.');
   if ((draft.password || '').length < 8) throw new Error('Le mot de passe doit contenir au moins 8 caractères.');
   if (page === 1 && draft.password !== draft.confirm) throw new Error('Les mots de passe ne correspondent pas.');
   if (page === 1) { const name = requiredName(); update(s => ({ ...s, profile: { ...s.profile, name, email: draft.email } })); go(3); }
   else if (page === 14) { update(s => ({ ...s, connected: true })); setDraft({}); setResult('Connecteur démo activé. Aucun accès bancaire réel.'); }
   else { go(4); notify('Session de démonstration ouverte.'); }
   return;
  }
  if (page === 3) { update(s => ({ ...s, profile: { ...s.profile, name: draft.name || s.profile.name, currency: draft.currency || 'Ar' } })); go(4); return; }
  if (page === 10) {
   const name = requiredName(), n = amount('amount'), date = validDate(), a = chosenAccount('from');
   const value = draft.type === 'Revenu' ? n : -n;
   addTransactions([{ id: id(), name, amount: value, date, category: draft.category || (value > 0 ? 'Revenu' : 'Alimentation'), account: a.id }]); go(9); notify('Transaction enregistrée.'); return;
  }
  if (page === 11) {
   const n = amount('amount'), from = chosenAccount('from'), to = chosenAccount('to', 1), date = validDate();
   if (!to || from.id === to.id) throw new Error('Choisissez deux comptes différents.');
   if (n > from.balance) throw new Error('Solde insuffisant pour ce transfert.');
   addTransactions([{ id: id(), name: `Vers ${to.name}`, amount: -n, category: 'Transfert', date, account: from.id }, { id: id(), name: `Depuis ${from.name}`, amount: n, category: 'Transfert', date, account: to.id }]); go(9); notify('Transfert effectué : le patrimoine total reste identique.'); return;
  }
  if (page === 17) {
   const n = amount('amount'), p = store.positions.find(p => p.name === draft.position) || store.positions[0], a = chosenAccount('from'), sell = draft.trade === 'Vendre';
   if (sell ? n > p.value : n > a.balance) throw new Error(sell ? 'La vente dépasse la valeur de la position.' : 'Solde insuffisant pour cet achat.');
   update(s => ({ ...s, positions: s.positions.map(v => v.id === p.id ? { ...v, value: v.value + (sell ? -n : n) } : v), accounts: s.accounts.map(v => v.id === a.id ? { ...v, balance: v.balance + (sell ? n : -n) } : v), transactions: [{ id: id(), name: `${sell ? 'Vente' : 'Achat'} ${p.name}`, amount: sell ? n : -n, category: 'Investissement', date: today(), account: a.id }, ...s.transactions] })); go(15); notify('Opération simulée enregistrée.'); return;
  }
  if (page === 24) {
   const initial = amount('initial', true), monthly = amount('monthly', true), months = amount('months');
   const rate = Number((draft.rate || '0').replace(',', '.'));
   if (!Number.isInteger(months) || months > 600 || !Number.isFinite(rate) || rate < -100 || rate > 100) throw new Error('Durée : 1 à 600 mois. Taux annuel : −100 à 100 %.');
   const future = project(initial, monthly, months, rate);
   setResult(`Capital simulé : ${fmt(future)}\nVersements : ${fmt(initial + monthly * months)}\nVariation simulée : ${fmt(future - initial - monthly * months)}\nHypothèse fixe, sans frais ni fiscalité. Ce résultat n’est pas une prévision.`); return;
  }
 }

 function getRows(): { key: string; cells: string[]; target?: number }[] {
  if ([5, 6].includes(page)) {
   const rows = [
    ...store.accounts.map(v => ({ key: v.id, cells: [v.name, fmt(v.balance), 'Actif'], target: 8 })),
    ...store.positions.map(v => ({ key: v.id, cells: [v.name, fmt(v.value), 'Actif'], target: 16 })),
    ...store.properties.map(v => ({ key: v.id, cells: [v.name, fmt(v.value), 'Actif'], target: 19 })),
    ...store.loans.map(v => ({ key: v.id, cells: [v.name, fmt(v.balance), 'Dette'], target: 21 })),
   ];
   return rows;
  }
  if ([9, 23].includes(page)) return store.transactions.map(v => ({ key: v.id, cells: [v.name, (v.amount > 0 ? '+' : '') + fmt(v.amount), v.category], target: 8 }));
  if (page === 12) return store.budgets.map(v => ({ key: v.id, cells: [v.name, fmt(v.limit), 'Budget'] }));
  if ([15, 16].includes(page)) return store.positions.filter(v => page !== 16 || v.id === selected || (!store.positions.some(p => p.id === selected) && v.id === store.positions[0].id)).map(v => ({ key: v.id, cells: [v.name, fmt(v.value), `${v.performance > 0 ? '+' : ''}${v.performance} %`], target: 16 }));
  if ([18, 19].includes(page)) return store.properties.filter(v => page !== 19 || v.id === selected || (!store.properties.some(p => p.id === selected) && v.id === store.properties[0].id)).map(v => ({ key: v.id, cells: [v.name, fmt(v.value), 'Actif'], target: 19 }));
  if ([20, 21].includes(page)) return store.loans.filter(v => page !== 21 || v.id === selected || (!store.loans.some(p => p.id === selected) && v.id === store.loans[0].id)).map(v => ({ key: v.id, cells: [v.name, fmt(v.balance), String(v.year)], target: 21 }));
  if (page === 25) return ['Novembre 2026', 'Octobre 2026', 'Septembre 2026'].map(v => ({ key: v, cells: [v, 'CSV / partage', 'Prêt'] }));
  if (page === 32) return store.alerts.map(v => ({ key: v.id, cells: [v.name, fmt(v.threshold), v.enabled ? 'Activée' : 'Désactivée'] }));
  return store.accounts.filter(v => page !== 8 || v.id === selected || (!store.accounts.some(p => p.id === selected) && v.id === store.accounts[0].id)).map(v => ({ key: v.id, cells: [v.name, fmt(v.balance), v.kind], target: 8 }));
 }

 function addEntry() {
  if ([9, 8, 23].includes(page)) return go(10);
  if ([15, 16].includes(page)) return go(17);
  if (page === 25) return void exportRows('Rapport patrimonial', [['Patrimoine net', fmt(t.net), 'Démo'], ['Actifs', fmt(t.assets), 'Démo'], ['Dettes', fmt(t.debt), 'Démo'], ...store.transactions.map(v => [v.name, fmt(v.amount), v.category])]);
  setEditor(true); setDraft({}); setError('');
 }
 function saveEntry() {
  const name = requiredName(), n = amount('amount');
  if ([18, 19].includes(page)) update(s => ({ ...s, properties: [...s.properties, { id: id(), name, value: n }] }));
  else if ([20, 21].includes(page)) { const year = Number(draft.year); if (!Number.isInteger(year) || year < new Date().getFullYear() || year > 2100) throw new Error('Saisissez une année d’échéance valide.'); update(s => ({ ...s, loans: [...s.loans, { id: id(), name, balance: n, year }] })); }
  else if (page === 12) { if (store.budgets.some(b => b.name === name)) throw new Error('Cette catégorie possède déjà un budget.'); update(s => ({ ...s, budgets: [...s.budgets, { id: id(), name, limit: n }] })); }
  else if (page === 32) update(s => ({ ...s, alerts: [...s.alerts, { id: id(), name, threshold: n, enabled: true }] }));
  else update(s => ({ ...s, accounts: [...s.accounts, { id: id(), name, balance: n, kind: draft.kind || 'Courant' }] }));
  setEditor(false); setDraft({}); notify('Élément ajouté et sauvegardé.');
 }

 return { store, ready, storageError, page, history, menu, setMenu, query, setQuery, filter, setFilter, showFilter, setShowFilter, selected, setSelected, toast, setToast, editor, setEditor, draft, setDraft, error, setError, result, setResult, csv, setCsv, preview, setPreview, question, setQuestion, messages, setMessages, natural, setNatural, scroll, t, fmt, notify, go, back, update, change, amount, run, field, pick, accountPicker, chosenAccount, requiredName, validDate, addTransactions, exportRows, link, info, actions, submitForm, getRows, addEntry, saveEntry };
}
export type WealthController = ReturnType<typeof useWealthController>;
export const WealthContext = createContext<WealthController | null>(null);
export function useWealth() {
 const context = useContext(WealthContext);
 if (!context) throw new Error('WealthContext manquant');
 return context;
}
