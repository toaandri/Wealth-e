import React, { useState } from 'react';
import { Text, View } from 'react-native';
import { styles } from '../theme/styles';
export function Card({ title, children }: { title?: string; children: React.ReactNode }) {
 return <View style={styles.card}>{title && <Text style={styles.cardTitle}>{title}</Text>}{children}</View>;
}
