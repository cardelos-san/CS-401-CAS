-- Table schemas
CREATE TABLE categories (
    category_id smallint(5) unsigned NOT NULL auto_increment PRIMARY KEY,
    name varchar(50) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COLLATE=utf8_unicode_ci;


CREATE TABLE inventory_category_map (
    item_id mediumint(8) unsigned NOT NULL,
    category_id smallint(5) unsigned NOT NULL,
    PRIMARY KEY (item_id, category_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COLLATE=utf8_unicode_ci;


CREATE TABLE inventory (
    item_id mediumint(8) unsigned NOT NULL auto_increment PRIMARY KEY,
    status enum('lost', 'retrieved')
    date_created timestamp NOT NULL default CURRENT_TIMESTAMP,
    date_found DATE NOT NULL,
    date_retrieved DATE NOT NULL,
    added_by_user mediumint(8) unsigned NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COLLATE=utf8_unicode_ci;


CREATE TABLE inventory_descriptions (
    item_id mediumint(8) unsigned NOT NULL PRIMARY KEY,
    description text NOT NULL FULLTEXT
) ENGINE=MyISAM


CREATE TABLE retrieval_records (
    retrieval_id mediumint(8) unsigned NOT NULL auto_increment PRIMARY KEY,
    item_id mediumint(8) unsigned NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    identification varchar(30) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COLLATE=utf8_unicode_ci;


CREATE TABLE users (
    user_id mediumint(8) unsigned NOT NULL auto_increment PRIMARY KEY,
    email varchar(50) NOT NULL,
    password varchar(100),
    role enum('admin', 'user') NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    time_registered timestamp NOT NULL default CURRENT_TIMESTAMP
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COLLATE=utf8_unicode_ci;


-- Indexes
CREATE INDEX inventory_date_found       ON inventory (date_found);
CREATE INDEX inventory_date_retrieved   ON inventory (date_retrieved);
CREATE INDEX inventory_added_by_user    ON inventory (added_by_user);


-- Foreign key constraints
ALTER TABLE inventory_descriptions ADD CONSTRAINT inventory_descriptions_item_id_fkey     FOREIGN KEY (item_id)     REFERENCES inventory  (item_id)     ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE retrieval_records      ADD CONSTRAINT retrieval_records_item_id_fkey          FOREIGN KEY (item_id)     REFERENCES inventory  (item_id)     ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE inventory_category_map ADD CONSTRAINT inventory_category_map_item_id_fkey     FOREIGN KEY (item_id)     REFERENCES inventory  (item_id)     ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE inventory_category_map ADD CONSTRAINT inventory_category_map_category_id_fkey FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE ON UPDATE CASCADE;
