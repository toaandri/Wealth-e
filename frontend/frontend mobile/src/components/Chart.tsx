import React, { useState } from 'react';
import { Text, View } from 'react-native';
import { styles } from '../theme/styles';
import { Card } from './Card';
import { Choices } from './Choices';
export function Chart({ title = 'Tendance et historique', periods = false }: { title?: string; periods?: boolean }) {
 const [period, setPeriod] = useState('1Y');
 const values = period === '1M' ? [20, 30, 25, 40, 37, 47, 42, 57, 60, 54, 66, 75] : period === '3M' ? [24, 26, 35, 32, 44, 49, 46, 57, 61, 63, 71, 80] : [30, 39, 35, 44, 42, 53, 50, 57, 56, 65, 72, 78];
 return <Card title={title}>{periods && <Choices values={['1M', '3M', '1Y', 'ALL']} value={period} onChange={setPeriod} />}<View style={styles.chart}>{values.map((v, i) => <View key={i} style={[styles.bar, { height: v + 12, backgroundColor: i % 2 ? '#38aa9c' : '#55bfb0' }]} />)}</View><View style={styles.between}><Text style={styles.tiny}>Jan</Text><Text style={styles.tiny}>Juin</Text><Text style={styles.tiny}>Déc</Text></View><Text style={[styles.tiny, { marginTop: 8 }]}>Évolution illustrative · données de démonstration</Text></Card>;
}

