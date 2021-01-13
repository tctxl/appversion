CREATE TABLE IF NOT EXISTS t_app (update_time TIMESTAMP,create_time TIMESTAMP,icon VARCHAR,app_desc VARCHAR,platform VARCHAR,app_name VARCHAR,id INTEGER NOT NULL IDENTITY (1000, 1),PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS t_app_channel (id INTEGER IDENTITY,app_id INTEGER,version_name VARCHAR,version_code INTEGER,channel VARCHAR,type INTEGER,share_top INTEGER,title VARCHAR,content VARCHAR,url VARCHAR(500),next_id INTEGER(11),app_open INTEGER(1),create_time TIMESTAMP,update_time TIMESTAMP,PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS t_app_version (id INTEGER IDENTITY,version_name VARCHAR,version_code INTEGER,app_id INTEGER,create_time TIMESTAMP,update_time TIMESTAMP,PRIMARY KEY (id));
