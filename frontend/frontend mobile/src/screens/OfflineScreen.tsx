import React from 'react';
import { Switch, Text, View } from 'react-native';
import { C, styles } from '../theme/styles';
import { Card } from '../components';

import { useWealth } from '../state/WealthContext';
export default function OfflineScreen() {
 const { store, t, fmt, update } = useWealth();
 return <><Card title="Mode hors-ligne"><View style={styles.between}><Text style={styles.label}>Consultation uniquement</Text><Switch accessibilityLabel="Activer le mode hors-ligne en lecture seule" value={store.offline} onValueChange={offline => update(s => ({ ...s, offline }))} trackColor={{ true: C.teal }} /></View><Text style={styles.body}>L’application conserve ses données sur cet appareil. En lecture seule, les opérations de modification sont suspendues.</Text><View style={styles.notice}><Text style={styles.noticeText}>Dernière sauvegarde : {new Date(store.updatedAt).toLocaleString('fr-FR')}{'\n'}{store.accounts.length} comptes · {store.transactions.length} transactions</Text></View><Text style={styles.statValue}>{fmt(t.net)}</Text></Card><Card title="Dernières données disponibles">{store.accounts.map(a => <View style={styles.tableRow} key={a.id}><Text style={styles.body}>{a.name}</Text><Text style={styles.label}>{fmt(a.balance)}</Text></View>)}</Card></>;
}
