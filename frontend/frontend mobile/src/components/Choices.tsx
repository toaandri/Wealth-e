import React, { useState } from 'react';
import { Pressable, Text, View } from 'react-native';
import { C, styles } from '../theme/styles';
export function Choices({ values, value, onChange }: { values: string[]; value: string; onChange: (v: string) => void }) {
 return <View style={styles.wrap}>{values.map(v => <Pressable accessibilityRole="button" accessibilityState={{ selected: v === value }} key={v} onPress={() => onChange(v)} style={[styles.chip, value === v && styles.selectedChip]}><Text style={{ color: value === v ? C.white : C.teal, fontSize: 12, fontWeight: '600' }}>{v}</Text></Pressable>)}</View>;
}
