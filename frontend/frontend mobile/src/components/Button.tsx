import React, { useState } from 'react';
import { Pressable, Text } from 'react-native';
import { C, styles } from '../theme/styles';
export function Button({ title, onPress, secondary = false, disabled = false }: { title: string; onPress: () => void; secondary?: boolean; disabled?: boolean }) {
 return <Pressable accessibilityRole="button" disabled={disabled} onPress={onPress} style={({ pressed }) => [styles.button, secondary && styles.secondary, (pressed || disabled) && { opacity: .55 }]}><Text style={[styles.buttonText, secondary && { color: C.teal }]}>{title}</Text></Pressable>;
}
