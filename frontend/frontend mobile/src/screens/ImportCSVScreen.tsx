import React from 'react';
import { Text, View } from 'react-native';
import { styles } from '../theme/styles';
import { Button, Card, Field } from '../components';

import { parseCSV } from '../model';
import { useWealth } from '../state/WealthContext';
export default function ImportCSVScreen() {
 const { error, csv, setCsv, preview, setPreview, fmt, notify, go, amount, run, accountPicker, chosenAccount, addTransactions, info } = useWealth();
 return <><Card title="Import CSV"><Text style={styles.body}>Collez un CSV avec les colonnes date;libelle;montant;categorie. Les montants négatifs sont des dépenses. Format simple sans séparateur à l’intérieur d’un champ.</Text>{accountPicker('from')}<Field label="Contenu CSV" value={csv} onChangeText={v => { setCsv(v); setPreview([]); }} multiline /><View style={styles.wrap}><Button title="Prévisualiser" onPress={() => run(() => { setPreview(parseCSV(csv, chosenAccount('from').id)); })} /></View>{!!error && <Text style={styles.error}>{error}</Text>}{preview.map(r => <View key={r.id} style={styles.tableRow}><Text style={styles.body}>{r.date} · {r.name}</Text><Text style={styles.label}>{fmt(r.amount)}</Text></View>)}{preview.length > 0 && <Button title={`Importer ${preview.length} opération(s)`} onPress={() => { addTransactions(preview.map(r => ({ ...r, account: chosenAccount('from').id }))); setPreview([]); setCsv(''); notify('Import effectué une seule fois.'); go(9); }} />}</Card>{info}</>;
}
