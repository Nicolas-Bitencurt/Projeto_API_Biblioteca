-- Inserir autores de exemplo
INSERT INTO autores (nome, biografia, data_criacao, data_atualizacao) VALUES
('Machado de Assis', 'Escritor e poeta brasileiro do século XIX', NOW(), NOW()),
('Clarice Lispector', 'Escritora brasileira de prosa poética', NOW(), NOW()),
('Paulo Coelho', 'Autor de livros de autoajuda e ficção', NOW(), NOW());

-- Inserir categorias de exemplo
INSERT INTO categorias (nome, descricao, data_criacao, data_atualizacao) VALUES
('Romance', 'Livros de narrativa ficcional', NOW(), NOW()),
('Ficção Científica', 'Histórias ambientadas em cenários futuristas', NOW(), NOW()),
('Autoajuda', 'Livros de desenvolvimento pessoal', NOW(), NOW()),
('Poesia', 'Obras poéticas', NOW(), NOW());

-- Inserir livros de exemplo
INSERT INTO livros (titulo, isbn, descricao, ano_publicacao, quantidade_total, quantidade_disponivel, autor_id, categoria_id, data_criacao, data_atualizacao) VALUES
('Dom Casmurro', '978-8535914443', 'Um dos maiores clássicos da literatura brasileira', 1899, 5, 5, 1, 1, NOW(), NOW()),
('Memórias Póstumas de Brás Cubas', '978-8535911770', 'Obra-prima de Machado de Assis', 1881, 3, 3, 1, 1, NOW(), NOW()),
('A Hora da Estrela', '978-8532511140', 'Novela curta e poética de Clarice Lispector', 1977, 4, 4, 2, 4, NOW(), NOW()),
('O Alquimista', '978-8532613517', 'Um clássico da autoajuda e espiritualidade', 1988, 6, 6, 3, 3, NOW(), NOW());
