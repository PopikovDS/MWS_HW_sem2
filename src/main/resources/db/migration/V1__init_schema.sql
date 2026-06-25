-- Создание таблицы tasks
CREATE TABLE IF NOT EXISTS tasks (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  description VARCHAR(1000) NOT NULL,
  completed BOOLEAN NOT NULL DEFAULT FALSE,
  priority VARCHAR(20) DEFAULT 'MEDIUM',
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

-- Создание таблицы вложений
CREATE TABLE IF NOT EXISTS task_attachments (
  id BIGSERIAL PRIMARY KEY,
  task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
  file_name VARCHAR(255),
  stored_file_name VARCHAR(255),
  content_type VARCHAR(100),
  size BIGINT,
  uploaded_at TIMESTAMP
);
