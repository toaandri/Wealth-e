type EmptyStateProps = {
  title: string;          // titre court, par exemple « Aucun compte »
  message?: string;       // explication facultative
  actionLabel?: string;   // texte du bouton, par exemple « Ajouter un compte »
  onAction?: () => void;  // ce que fait le bouton quand on clique
};

export function EmptyState({ title, message, actionLabel, onAction }: EmptyStateProps) {
  return (
    <div className="state">
      <p className="state__title">{title}</p>

      {message ? <p className="state__message">{message}</p> : null}

      {actionLabel && onAction ? (
        <button type="button" className="button" onClick={onAction}>
          {actionLabel}
        </button>
      ) : null}
    </div>
  );
}
