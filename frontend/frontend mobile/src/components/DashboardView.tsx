import React from 'react';
import { Text, View } from 'react-native';
import { C, styles } from '../theme/styles';
import { Button, Card, Chart } from '../components';

import { recommend } from '../model';
import { useWealth } from '../state/WealthContext';

export function DashboardView() {
 const { store, page, t, fmt, go, link } = useWealth();

  return <>{page === 0 && <Card title="Votre patrimoine, en un seul endroit"><Text style={styles.body}>Comptes, placements, biens, dettes et analyses — sans connexion bancaire réelle.</Text><View style={styles.wrap}><Button title="Explorer la démo" onPress={() => go(4)} /><Button title="Créer un compte" secondary onPress={() => go(1)} /></View>{link('J’ai déjà un compte démo', 2)}</Card>}
   <View style={styles.stats}>{[{ name: 'Patrimoine net', value: t.net, note: '↗ +4,2 % · exemple' }, { name: 'Actifs', value: t.assets, note: '5 catégories' }, { name: 'Dettes', value: t.debt, note: `${store.loans.length} prêts` }].map(v => <View key={v.name} style={styles.stat}><Text style={styles.body}>{v.name}</Text><Text adjustsFontSizeToFit numberOfLines={1} style={styles.statValue}>{fmt(v.value)}</Text><Text style={[styles.tiny, v.name === 'Patrimoine net' && { color: C.teal }]}>{v.note}</Text></View>)}</View>
   <Chart title="Évolution du patrimoine" periods />
   <Card title="Répartition">{[['Immobilier', t.property], ['Liquidités', t.cash], ['Investissements', t.investments]].map(([name, value]) => <View key={String(name)} style={styles.tableRow}><Text style={styles.body}>{name}</Text><Text style={styles.label}>{Math.round(Number(value) / Math.max(t.assets, 1) * 100)} %</Text></View>)}</Card>
   {page === 4 && <><Card title="Recommandations de démonstration">{recommend(store).map(r => <View key={r.title} style={{ marginBottom: 14 }}><Text style={styles.label}>{r.title}</Text><Text style={styles.body}>{r.body}</Text>{link('Explorer', r.page)}</View>)}<Text style={styles.tiny}>Règles locales fondées sur vos données démo ; aucun conseil personnalisé.</Text></Card><Card title="Accès rapide">{link('Nouvelle transaction', 10, 'add-circle-outline')}{link('Transfert interne', 11, 'swap-horizontal')}{link('Assistant Wealth-e', 28, 'sparkles-outline')}{link('Mes portefeuilles', 5)}</Card></>}
  </>;
 }

