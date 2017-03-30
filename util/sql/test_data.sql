START TRANSACTION;

-- Categories
INSERT INTO categories (category_id, name) VALUES
(1, "Cell Phones"),
(2, "Wallets"),
(3, "Books"),
(4, "Glasses"),
(5, "Headphones"),
(6, "Keys"),
(7, "Miscellaneous");

-- User
INSERT INTO users (user_id, email, passwd, role, first_name, last_name) VALUES
(1, 'test@example.net', 'nopasswd', 'admin', 'Admin', 'User');

-- Inventory
INSERT INTO inventory (item_id, description, `status`, date_found, date_retrieved, added_by_user) VALUES
(1, 'Brown leather wallet', 'lost', NOW(), NULL, 1),
(2, 'iPhone with light blue case', 'retrieved', NOW(), NOW(), 1);

-- Retrieval
INSERT INTO retrieval_records (retrieval_id, item_id, first_name, last_name, email, phone, identification) VALUES
(1, 2, 'Kaitlyn', 'Somename', 'kaitlyn@example.net', '4016559815', 'RI1865497');

-- Category Map
INSERT INTO inventory_category_map (item_id, category_id) VALUES
(1, 2),
(2, 1);

COMMIT;