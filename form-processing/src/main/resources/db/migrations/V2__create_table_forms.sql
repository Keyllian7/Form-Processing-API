CREATE TABLE forms (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    motivo TEXT NOT NULL,
    setor TEXT NOT NULL,
    mensagem TEXT,
    user_id UUID,
);
