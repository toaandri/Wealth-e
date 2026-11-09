type LoadingSkeletonProps = {
  lines?: number;  // nombre de lignes grises (3 par défaut)
  label?: string;  // texte lu par les lecteurs d'écran
};

export function LoadingSkeleton({ lines = 3, label = "Chargement en cours…" }: LoadingSkeletonProps) {
  return (
    <div className="skeleton" role="status" aria-live="polite" aria-busy="true">
      <span className="visually-hidden">{label}</span>

      {Array.from({ length: lines }, (_, index) => (
        <div key={index} className="skeleton__line" aria-hidden="true" />
      ))}
    </div>
  );
}
