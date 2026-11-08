type ErrorStateProps = {
  title?: string;         // titre (un titre par défaut est prévu)
  message?: string;       // explication facultative
  onRetry?: () => void;   // ce que fait le bouton « Réessayer »
};

export function ErrorState({
  title = "Impossible de charger les données",
  message = "Vérifiez votre connexion, puis réessayez.",
  onRetry,
}: ErrorStateProps) {
  return (
    <div className="state state--error" role="alert">
      <p className="state__title">{title}</p>
      <p className="state__message">{message}</p>

      {onRetry ? (
        <button type="button" className="button" onClick={onRetry}>
          Réessayer
        </button>
      ) : null}
    </div>
  );
}
