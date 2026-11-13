import React from 'react';
import { Pressable, Text, View } from 'react-native';
import { C, styles } from '../theme/styles';
import { Button, Card, Field, Choices, Chart } from '../components';
import { screens } from '../navigation/routes';

import { useWealth } from '../state/WealthContext';
import { today } from '../utils/format';
export function EntityListView() {
 const { store, page, query, setQuery, filter, setFilter, showFilter, setShowFilter, selected, setSelected, editor, t, fmt, go, update, amount, field, pick, exportRows, link, actions, getRows, addEntry, saveEntry } = useWealth();

  const all = getRows();
  const rows = all.filter(r => r.cells.join(' ').toLowerCase().includes(query.toLowerCase()) && (filter === 'Tous' || r.cells[2] === filter));
  return <>
   <Card title={screens[page][0] + ' — aperçu'}><View style={styles.wrap}><Button title={page === 25 ? 'Générer un rapport' : '+ Ajouter / créer'} disabled={store.offline && page !== 25} onPress={addEntry} /><Button title="Filtrer" secondary onPress={() => setShowFilter(v => !v)} /><Button title="Exporter" secondary onPress={() => void exportRows(screens[page][0], rows.map(r => r.cells))} /></View>
    {showFilter && <><Field label="Rechercher un élément" value={query} onChangeText={setQuery} /><Choices values={['Tous', ...Array.from(new Set(all.map(r => r.cells[2])))]} value={filter} onChange={setFilter} /></>}
    <View style={styles.tableHead}>{['ÉLÉMENT', 'VALEUR', 'ÉTAT'].map((v, i) => <Text key={v} style={[styles.column, styles.tableLabel, { flex: i === 0 ? 1.3 : 1 }]}>{v}</Text>)}</View>
    {rows.map(r => <Pressable accessibilityRole="button" accessibilityLabel={r.cells.join(', ')} disabled={store.offline && page === 32} key={r.key} style={styles.tableRow} onPress={() => { if (page === 32) update(s => ({ ...s, alerts: s.alerts.map(a => a.id === r.key ? { ...a, enabled: !a.enabled } : a) })); else if (page === 25) void exportRows(r.cells[0], store.transactions.map(t => [t.name, fmt(t.amount), t.category])); else if (r.target !== undefined) { const selection = [9, 23].includes(page) ? store.transactions.find(v => v.id === r.key)?.account : r.key; go(r.target, selection); } }}>
     {r.cells.map((cell, i) => <Text key={i} style={[styles.column, { flex: i === 0 ? 1.3 : 1 }, i === 2 && { color: cell.startsWith('-') ? C.red : C.teal }]}>{cell}</Text>)}
    </Pressable>)}{rows.length === 0 && <Text style={[styles.body, { padding: 16 }]}>Aucun élément ne correspond à votre recherche.</Text>}
   </Card>
   {editor && <Card title="Ajouter un élément">{field('name', page === 12 ? 'Catégorie (ex. Transport)' : 'Nom / libellé')}{field('amount', page === 32 ? 'Seuil (' + store.profile.currency + ')' : 'Montant (' + store.profile.currency + ')', true)}{[20, 21].includes(page) && field('year', 'Année d’échéance', true)}{[5, 6, 7].includes(page) && pick('kind', ['Courant', 'Épargne', 'Courtage'])}{actions(saveEntry)}</Card>}
   {page === 12 && <Card title="Suivi des budgets">{store.budgets.map(b => { const spent = -store.transactions.filter(v => v.category === b.name && v.amount < 0 && v.date.slice(0, 7) === today().slice(0, 7)).reduce((a, v) => a + v.amount, 0); return <View key={b.id} style={{ marginBottom: 16 }}><View style={styles.between}><Text style={styles.label}>{b.name}</Text><Text style={styles.tiny}>{Math.round(spent / b.limit * 100)} %</Text></View><View style={styles.track}><View style={{ width: `${Math.min(spent / b.limit * 100, 100)}%`, height: 6, backgroundColor: spent > b.limit ? C.red : C.teal, borderRadius: 3 }} /></View><Text style={styles.body}>{fmt(spent)} / {fmt(b.limit)}</Text></View>; })}<Text style={styles.tiny}>Opérations du mois en cours, hors transferts internes.</Text></Card>}
   <View style={styles.summary}><Card title="Résumé"><Text style={styles.statValue}>{fmt([20, 21].includes(page) ? t.debt : page === 8 ? store.accounts.find(a => a.id === selected)?.balance ?? store.accounts[0].balance : t.net)}</Text><Text style={styles.body}>Valeurs fictives pour la présentation.</Text><View style={styles.notice}><Text style={styles.noticeText}>Source : DEMO{ '\n' }Mis à jour : {new Date(store.updatedAt).toLocaleDateString('fr-FR')}</Text></View></Card></View>
   <Chart />
   {page === 5 && link('Voir les comptes', 7)}{[7, 8].includes(page) && <Card title="Opérations">{link('Transactions', 9)}{link('Transfert interne', 11)}{link('Budgets', 12)}{link('Importer un CSV', 13)}{link('Connexions simulées', 14)}</Card>}
   {[15, 16].includes(page) && link('Acheter / Vendre', 17)}{page === 18 && link('Dettes & prêts', 20)}
   {page === 22 && <><Card title="Allocation des actifs">{[['Immobilier', t.property], ['Liquidités', t.cash], ['Investissements', t.investments]].map(([name, n]) => <View key={String(name)} style={styles.tableRow}><Text style={styles.body}>{name}</Text><Text style={styles.label}>{Math.round(Number(n) / Math.max(t.assets, 1) * 100)} %</Text></View>)}</Card><Card title="Explorer les analyses">{link('Flux de trésorerie', 23)}{link('Scénarios', 24)}{link('Rapports', 25)}{link('Explication IA', 29)}</Card></>}
   {page === 23 && <Card title="Flux hors transferts">{[['Revenus', store.transactions.filter(v => v.amount > 0 && v.category !== 'Transfert' && v.category !== 'Investissement').reduce((a, v) => a + v.amount, 0)], ['Dépenses', -store.transactions.filter(v => v.amount < 0 && v.category !== 'Transfert' && v.category !== 'Investissement').reduce((a, v) => a + v.amount, 0)]].map(([name, value]) => <View key={String(name)} style={styles.tableRow}><Text style={styles.body}>{name}</Text><Text style={styles.label}>{fmt(Number(value))}</Text></View>)}</Card>}
   {page === 32 && <Text style={styles.body}>Touchez une alerte pour l’activer ou la désactiver. Les alertes restent dans l’application.</Text>}
  </>;
 }

