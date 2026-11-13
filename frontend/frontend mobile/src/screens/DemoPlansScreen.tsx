import React from 'react';
import { Text, View } from 'react-native';
import { styles } from '../theme/styles';
import { Button, Card } from '../components';

import { useWealth } from '../state/WealthContext';
export default function DemoPlansScreen() {
 const { store, notify, update } = useWealth();
 return <><Card title="Plans démo"><Text style={styles.body}>Choisissez un plan fictif. Aucun paiement n’est effectué.</Text>{['Free', 'Plus'].map(plan => <View key={plan} style={styles.plan}><Text style={styles.statValue}>{plan}</Text><Text style={styles.body}>{plan === 'Free' ? 'Comptes, transactions et synthèse patrimoniale.' : 'Analyses, simulations et assistant de démonstration.'}</Text><Button title={store.plan === plan ? 'Plan actif' : 'Choisir ' + plan} secondary={store.plan !== plan} disabled={store.plan === plan} onPress={() => { update(s => ({ ...s, plan })); notify('Plan démo mis à jour.'); }} /></View>)}</Card></>;
}
