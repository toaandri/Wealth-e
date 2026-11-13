import React, { useState } from 'react';
import { Text, TextInput, View } from 'react-native';
import { styles } from '../theme/styles';
export function Field({ label, value, onChangeText, numeric, secret, multiline }: { label: string; value: string; onChangeText: (v: string) => void; numeric?: boolean; secret?: boolean; multiline?: boolean }) {
 return <View style={{ marginTop: 12 }}><Text style={styles.label}>{label}</Text><TextInput accessibilityLabel={label} placeholder={label} placeholderTextColor="#8b96a5" value={value} onChangeText={onChangeText} secureTextEntry={secret} keyboardType={numeric ? 'decimal-pad' : label.includes('mail') ? 'email-address' : 'default'} autoCapitalize={secret || label.includes('mail') ? 'none' : 'sentences'} multiline={multiline} style={[styles.input, multiline && { minHeight: 100, textAlignVertical: 'top' }]} /></View>;
}
