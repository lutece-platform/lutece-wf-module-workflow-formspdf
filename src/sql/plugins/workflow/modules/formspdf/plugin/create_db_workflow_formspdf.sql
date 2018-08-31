--
-- Table structure for table tf_forms_cf
--
DROP TABLE IF EXISTS task_create_pdf_cf;
CREATE TABLE task_create_pdf_cf(
  id_task INT NOT NULL,
  id_form INT DEFAULT NULL,
  id_question_url_pdf INT DEFAULT NULL,
  id_config INT DEFAULT NULL,
  PRIMARY KEY (id_task)
);