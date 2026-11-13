import React from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { Button, Card, Choices, Field } from '../components';
import { C, styles } from '../theme/styles';
import { useWealth } from '../state/WealthContext';

type Icon = React.ComponentProps<typeof Ionicons>['name'];
function SettingRow({ icon, title, description, value, onPress }: { icon: Icon; title: string; description: string; value?: string; onPress: () => void }) {
 return <Pressable accessibilityRole="button" accessibilityLabel={`${title}${value ? ', ' + value : ''}`} onPress={onPress} style={({ pressed }) => [local.row, pressed && { opacity: .6 }]}>
  <View style={local.icon}><Ionicons name={icon} size={20} color={C.teal} /></View>
  <View style={{ flex: 1 }}><Text style={styles.label}>{title}</Text><Text style={styles.tiny}>{description}</Text></View>
  {value && <Text style={local.value}>{value}</Text>}<Ionicons name="chevron-forward" size={16} color={C.muted} />
 </Pressable>;
}

export default function ProfilePreferencesScreen() {
 const { store, draft, setDraft, error, result, setError, setResult, update, run, go } = useWealth();
 const name = draft.name ?? store.profile.name;
 const email = draft.email ?? store.profile.email;
 const currency = draft.currency ?? store.profile.currency;
 const dirty = name !== store.profile.name || email !== store.profile.email || currency !== store.profile.currency;
 const edit = (key: string) => (value: string) => { setDraft(d => ({ ...d, [key]: value })); setError(''); setResult(''); };
 const save = () => run(() => {
  if (store.offline) throw new Error('Désactivez la lecture seule pour modifier vos préférences.');
  if (!name.trim()) throw new Error('Le nom est obligatoire.');
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.trim())) throw new Error('Adresse e-mail invalide.');
  update(s => ({ ...s, profile: { name: name.trim(), email: email.trim(), currency } }));
  setDraft({}); setResult('Vos préférences ont été enregistrées.');
 });
 return <>
  <Card><View style={local.identity}><View style={local.avatar}><Text style={local.initial}>{store.profile.name.trim().slice(0, 1).toUpperCase()}</Text></View><View style={{ flex: 1 }}><Text style={styles.statValue}>{store.profile.name}</Text><Text style={styles.body}>{store.profile.email}</Text><Text style={local.accountType}>Profil de démonstration · {store.plan}</Text></View></View></Card>
  {store.offline && <Card title="Lecture seule activée"><Text style={styles.body}>Vous pouvez consulter les paramètres. Désactivez la lecture seule pour modifier votre profil.</Text><SettingRow icon="cloud-offline-outline" title="Gérer le mode hors-ligne" description="Accéder aux options de consultation" onPress={() => go(33)} /></Card>}
  <Card title="Informations personnelles"><Text style={styles.body}>Les informations de ce profil restent sur cet appareil.</Text><View pointerEvents={store.offline ? 'none' : 'auto'} style={store.offline ? { opacity: .6 } : undefined}><Field label="Nom" value={name} onChangeText={edit('name')} /><Field label="Adresse e-mail" value={email} onChangeText={edit('email')} /></View></Card>
  <Card title="Préférences d’affichage"><Text style={styles.label}>Devise</Text><View pointerEvents={store.offline ? 'none' : 'auto'}><Choices values={['Ar', 'EUR', 'USD']} value={currency} onChange={edit('currency')} /></View><Text style={styles.tiny}>La devise modifie le libellé des montants de démonstration, sans conversion.</Text></Card>
  <View style={local.save}><Button title="Enregistrer les modifications" disabled={!dirty || store.offline} onPress={save} />{dirty && <Button title="Annuler les modifications" secondary onPress={() => { setDraft({}); setError(''); setResult(''); }} />}{!!error && <Text accessibilityRole="alert" style={styles.error}>{error}</Text>}{!!result && <View accessibilityLiveRegion="polite" style={styles.notice}><Text style={styles.noticeText}>{result}</Text></View>}</View>
  <Card title="Abonnement & suivi"><SettingRow icon="ribbon-outline" title="Mon plan démo" description="Comparer les offres fictives Free et Plus" value={store.plan} onPress={() => go(26)} /><SettingRow icon="notifications-outline" title="Notifications & alertes" description="Gérer les seuils et les alertes locales" value={String(store.alerts.filter(a => a.enabled).length)} onPress={() => go(32)} /></Card>
  <Card title="Données & connexions"><SettingRow icon="cloud-offline-outline" title="Mode hors-ligne" description="Sauvegarde locale et consultation en lecture seule" value={store.offline ? 'Activé' : 'Désactivé'} onPress={() => go(33)} /><SettingRow icon="link-outline" title="Connexions simulées" description="Gérer le connecteur bancaire de démonstration" value={store.connected ? 'Connecté' : 'Inactif'} onPress={() => go(14)} /><SettingRow icon="download-outline" title="Mes rapports" description="Consulter et exporter les données de démonstration" onPress={() => go(25)} /></Card>
  <Card title="Assistant & aide"><SettingRow icon="sparkles-outline" title="Assistant Wealth-e" description="Questions, explications et recommandations locales" onPress={() => go(28)} /><SettingRow icon="book-outline" title="Glossaire éducatif" description="Comprendre les notions de patrimoine" onPress={() => go(31)} /></Card>
  <Card title="À propos de Wealth-e"><Text style={styles.body}>Version 1.0.0 · Démonstration mobile</Text><Text style={styles.tiny}>Données synthétiques enregistrées sur cet appareil. Aucune connexion bancaire réelle ni authentification distante.</Text><SettingRow icon="home-outline" title="Accueil démo" description="Revenir à la présentation de l’application" onPress={() => go(0)} /></Card>
 </>;
}
const local = StyleSheet.create({
 identity: { flexDirection: 'row', alignItems: 'center', gap: 14 }, avatar: { width: 54, height: 54, borderRadius: 18, backgroundColor: C.pale, justifyContent: 'center', alignItems: 'center' }, initial: { fontWeight: '800', fontSize: 25, color: C.teal }, accountType: { color: C.teal, fontSize: 11, marginTop: 6 }, row: { flexDirection: 'row', alignItems: 'center', gap: 10, minHeight: 72, paddingVertical: 12, borderBottomWidth: 1, borderColor: C.line }, icon: { width: 36, height: 36, borderRadius: 10, backgroundColor: C.pale, justifyContent: 'center', alignItems: 'center' }, value: { fontSize: 10, fontWeight: '600', color: C.teal, maxWidth: 58 }, save: { gap: 8, marginBottom: 20 },
});
