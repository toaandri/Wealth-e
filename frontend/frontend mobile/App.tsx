import React from 'react';
import { ActivityIndicator, KeyboardAvoidingView, Modal, Platform, Pressable, ScrollView, StatusBar, Text, View } from 'react-native';
import { SafeAreaProvider, SafeAreaView } from 'react-native-safe-area-context';
import { Ionicons } from '@expo/vector-icons';
import { Button, Card } from './src/components';
import { C, styles } from './src/theme/styles';
import { screens, groups } from './src/navigation/routes';
import { ScreenRouter } from './src/navigation/ScreenRouter';
import { WealthContext, useWealthController } from './src/state/WealthContext';
type Icon = React.ComponentProps<typeof Ionicons>['name'];
export default function App() { return <SafeAreaProvider><WealthApp /></SafeAreaProvider>; }
function WealthApp() {
 const controller = useWealthController();
 const { store, ready, storageError, page, menu, setMenu, toast, setToast, scroll, go, link } = controller;
 const readOnlyPage = store.offline && [1, 2, 3, 10, 11, 13, 14, 17, 26].includes(page);
 if (!ready) return <SafeAreaView style={[styles.safe, { justifyContent: 'center', alignItems: 'center' }]}><ActivityIndicator color={C.teal} /><Text style={styles.body}>Chargement de Wealth-e…</Text></SafeAreaView>;
 return <WealthContext.Provider value={controller}><SafeAreaView style={styles.safe}><StatusBar barStyle="dark-content" /><View style={styles.shell}>
  <View style={styles.header}><Pressable accessibilityRole="button" accessibilityLabel="Accueil Wealth-e" onPress={() => go(0)}><Text style={styles.logo}>◆ WEALTH-E</Text></Pressable><View style={styles.wrap}><View style={styles.badge}><Text style={styles.badgeText}>DEMO DATA</Text></View><Pressable accessibilityRole="button" accessibilityLabel="Toutes les pages dans l’ordre du PDF" onPress={() => setMenu(true)} hitSlop={12}><Ionicons name="grid-outline" color={C.ink} size={21} /></Pressable></View></View>
  {!!storageError && <Text style={styles.error}>{storageError}</Text>}
  {store.offline && <Pressable onPress={() => go(33)} style={styles.offline}><Text style={styles.noticeText}>Hors-ligne · lecture seule · gérer</Text></Pressable>}
  <KeyboardAvoidingView style={{ flex: 1 }} behavior={Platform.OS === 'ios' ? 'padding' : undefined}><ScrollView ref={scroll} style={{ flex: 1 }} contentContainerStyle={styles.content} keyboardShouldPersistTaps="handled">
   <View style={styles.pageHeading}><View style={styles.between}><Text style={styles.eyebrow}>{screens[page][1].toUpperCase()}</Text><Text style={styles.tiny}>{String(page + 1).padStart(2, '0')}/34</Text></View><Text style={styles.title}>{screens[page][0]}</Text><Text style={styles.body}>{screens[page][2]}</Text></View>
   {readOnlyPage ? <Card title="Consultation hors-ligne"><Text style={styles.body}>Désactivez le mode lecture seule pour utiliser ce formulaire.</Text>{link('Gérer le mode hors-ligne', 33)}</Card> : <ScreenRouter page={page} />}
   <Text style={styles.footer}>WEALTH-E · Maquette UI/UX conceptuelle · Données 100 % synthétiques · Pas de conseil financier.</Text>
   <View style={styles.between}><Button title="‹ Précédent" secondary disabled={page === 0} onPress={() => go(page - 1)} /><Text style={styles.tiny}>{page + 1} / 34</Text><Button title="Suivant ›" secondary disabled={page === 33} onPress={() => go(page + 1)} /></View>
  </ScrollView></KeyboardAvoidingView>
  {!!toast && <Pressable accessibilityLiveRegion="polite" onPress={() => setToast('')} style={styles.toast}><Text style={{ color: C.white, fontSize: 13 }}>{toast}</Text></Pressable>}
  <View style={styles.tabs}>{([{ title: 'Accueil', icon: 'home-outline', page: 4, active: page <= 6 }, { title: 'Comptes', icon: 'wallet-outline', page: 7, active: page >= 7 && page <= 14 }, { title: 'Actifs', icon: 'diamond-outline', page: 15, active: page >= 15 && page <= 21 }, { title: 'Analyses', icon: 'bar-chart-outline', page: 22, active: page >= 22 && page <= 25 }, { title: 'Profil', icon: 'person-outline', page: 27, active: page >= 26 }] as { title: string; icon: Icon; page: number; active: boolean }[]).map(tab => <Pressable accessibilityRole="tab" accessibilityState={{ selected: tab.active }} key={tab.title} onPress={() => go(tab.page)} style={styles.tab}><Ionicons name={tab.icon} size={20} color={tab.active ? C.teal : C.muted} /><Text style={[styles.tabLabel, { color: tab.active ? C.teal : C.muted }]}>{tab.title}</Text>{tab.active && <View style={styles.tabDot} />}</Pressable>)}</View>
 </View>
 <Modal visible={menu} animationType="slide" onRequestClose={() => setMenu(false)}><SafeAreaView style={styles.safe}><View style={styles.shell}><View style={styles.header}><Text style={styles.title}>Les 34 pages</Text><Pressable accessibilityRole="button" accessibilityLabel="Fermer le menu" onPress={() => setMenu(false)} hitSlop={15}><Ionicons name="close" size={26} color={C.ink} /></Pressable></View><ScrollView contentContainerStyle={styles.content}><Text style={styles.body}>Parcours dans l’ordre de la maquette PDF</Text>{groups.map(group => <View key={group.title} style={{ marginTop: 22 }}><Text style={styles.eyebrow}>{group.title.toUpperCase()}</Text>{group.pages.map(n => <Pressable accessibilityRole="button" key={n} onPress={() => go(n)} style={[styles.menuRow, n === page && { backgroundColor: C.pale }]}><Text style={[styles.menuNumber, n === page && { color: C.teal }]}>{String(n + 1).padStart(2, '0')}</Text><Text style={[styles.linkText, { flex: 1 }]}>{screens[n][0]}</Text><Ionicons name="chevron-forward" size={16} color={C.teal} /></Pressable>)}</View>)}</ScrollView></View></SafeAreaView></Modal>
 </SafeAreaView></WealthContext.Provider>;
}

