-- Table: a15_rptformat

-- DROP TABLE a15_rptformat;

CREATE TABLE a15_rptformat
(
  create_date timestamp without time zone, -- 登録日時
  create_term character varying(30), -- 登録端末
  create_user character varying(20), -- 登録ユーザー
  update_date timestamp without time zone, -- 更新日時
  update_term character varying(30), -- 更新端末
  update_user character varying(20), -- 更新ユーザー
  a15_rptformatcd character varying(15) NOT NULL, -- 帳票書式コード
  a15_rptformatnm character varying(40), -- 帳票書式名
  a15_tablenm character varying(40) NOT NULL, -- テーブル名&View名
  a15_ireportnm character varying(40), -- レポート名
  a15_datakb integer NOT NULL, -- データ区分
  a15_seq integer NOT NULL, -- 明細行
  a15_colnm character varying(128), -- 列名
  a15_coltype character varying(20), -- 列タイプ
  a15_sort integer, -- ソート区分
  a15_junjo integer, -- ソート順序
  a15_syurui integer, -- ソート種類
  a15_enzanshi integer, -- 演算子
  a15_shiki integer, -- 条件式
  a15_atai1 character varying(40), -- 条件値１
  a15_atai2 character varying(40), -- 条件値２
  a15_rptcolnm character varying(128), -- レポート項目名
  CONSTRAINT a15_rptformat_pk PRIMARY KEY (a15_rptformatcd, a15_tablenm, a15_datakb, a15_seq)
)
WITH (
  OIDS=TRUE
);
ALTER TABLE a15_rptformat OWNER TO postgres;
COMMENT ON TABLE a15_rptformat IS '帳票書式表';
COMMENT ON COLUMN a15_rptformat.create_date IS '登録日時';
COMMENT ON COLUMN a15_rptformat.create_term IS '登録端末';
COMMENT ON COLUMN a15_rptformat.create_user IS '登録ユーザー';
COMMENT ON COLUMN a15_rptformat.update_date IS '更新日時';
COMMENT ON COLUMN a15_rptformat.update_term IS '更新端末';
COMMENT ON COLUMN a15_rptformat.update_user IS '更新ユーザー';
COMMENT ON COLUMN a15_rptformat.a15_rptformatcd IS '帳票書式コード';
COMMENT ON COLUMN a15_rptformat.a15_rptformatnm IS '帳票書式名';
COMMENT ON COLUMN a15_rptformat.a15_tablenm IS 'テーブル名&View名';
COMMENT ON COLUMN a15_rptformat.a15_ireportnm IS 'レポート名';
COMMENT ON COLUMN a15_rptformat.a15_datakb IS 'データ区分';
COMMENT ON COLUMN a15_rptformat.a15_seq IS '明細行';
COMMENT ON COLUMN a15_rptformat.a15_colnm IS '列名';
COMMENT ON COLUMN a15_rptformat.a15_coltype IS '列タイプ';
COMMENT ON COLUMN a15_rptformat.a15_sort IS 'ソート区分';
COMMENT ON COLUMN a15_rptformat.a15_junjo IS 'ソート順序';
COMMENT ON COLUMN a15_rptformat.a15_syurui IS 'ソート種類';
COMMENT ON COLUMN a15_rptformat.a15_enzanshi IS '演算子';
COMMENT ON COLUMN a15_rptformat.a15_shiki IS '条件式';
COMMENT ON COLUMN a15_rptformat.a15_atai1 IS '条件値１';
COMMENT ON COLUMN a15_rptformat.a15_atai2 IS '条件値２';
COMMENT ON COLUMN a15_rptformat.a15_rptcolnm IS 'レポート項目名';

