# Analyse de la maquette mobile

34 pages inspectées visuellement après rendu PNG du PDF. Le texte intégré ne contient que les titres : les écrans eux-mêmes sont des images.

## Identité visuelle

- Fond gris bleuté très clair ; cartes blanches à coins arrondis et bordure discrète.
- Titres bleu marine, textes secondaires gris bleu, actions principales vert canard.
- Actions secondaires sur fond vert pâle ; badge DEMO DATA jaune clair.
- Marque en en-tête, rubrique en petites capitales, titre, sous-titre et séparateur.
- Cinq onglets persistants : Accueil, Comptes, Actifs, Analyses, Profil.
- Grille de synthèse avec patrimoine net 80 000 000 Ar, actifs 90 000 000 Ar, dettes 10 000 000 Ar.
- Graphiques à douze barres, résumés compacts, tableaux à trois colonnes.

## Familles d’écrans

1. Accueil / synthèse : 01, 05.
2. Formulaires : 02–04, 11–12, 14–15, 18, 25.
3. Listes et détails : 06–10, 13, 16–17, 19–24, 26–28, 33–34.
4. Assistant : 29–32.

L’ordre intégral et les intitulés sont définis dans `src/screens.ts`. Les adaptations fonctionnelles ajoutent des sélecteurs explicites de compte, des validations et des écrans de résultat. Le menu global et la pagination permettent de vérifier chaque écran dans l’ordre du document sans masquer les pages secondaires.
