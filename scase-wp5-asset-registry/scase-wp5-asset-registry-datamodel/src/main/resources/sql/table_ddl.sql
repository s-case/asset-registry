CREATE TABLE 'ARTEFACT' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'CREATED_AT' datetime DEFAULT NULL,
  'CREATEDBY' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'UPDATED_AT' datetime DEFAULT NULL,
  'UPDATEDBY' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'version' bigint(20) DEFAULT NULL,
  'DESCRIPTION' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'GROUPID' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'ARTEFACTNAME' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'TYPE' int(11) DEFAULT NULL,
  'URI' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'domain_id' bigint(20) DEFAULT NULL,
  'subDomain_id' bigint(20) DEFAULT NULL,
  PRIMARY KEY ('id'),
  KEY 'FK_j1l989vybbtgicc89a4dlrh40' ('domain_id'),
  KEY 'FK_6cc5q53onb3b7peus8jfj7n44' ('subDomain_id'),
  CONSTRAINT 'FK_6cc5q53onb3b7peus8jfj7n44' FOREIGN KEY ('subDomain_id') REFERENCES 'SUBDOMAIN' ('id'),
  CONSTRAINT 'FK_j1l989vybbtgicc89a4dlrh40' FOREIGN KEY ('domain_id') REFERENCES 'DOMAIN' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE 'ARTEFACT_ARTEFACTPAYLOAD' (
  'ARTEFACT_id' bigint(20) NOT NULL,
  'payload_id' bigint(20) NOT NULL,
  UNIQUE KEY 'UK_2qd8needk3y3ad25gwcjdyb1' ('payload_id'),
  KEY 'FK_6nnl7pmsu4on3rbxcfxswpeqj' ('ARTEFACT_id'),
  CONSTRAINT 'FK_2qd8needk3y3ad25gwcjdyb1' FOREIGN KEY ('payload_id') REFERENCES 'ARTEFACTPAYLOAD' ('id'),
  CONSTRAINT 'FK_6nnl7pmsu4on3rbxcfxswpeqj' FOREIGN KEY ('ARTEFACT_id') REFERENCES 'ARTEFACT' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE 'Artefact_dependencies' (
  'Artefact_id' bigint(20) NOT NULL,
  'dependencies' bigint(20) DEFAULT NULL,
  KEY 'FK_68hh8a943hpxm4r8gk9exjo3f' ('Artefact_id'),
  CONSTRAINT 'FK_68hh8a943hpxm4r8gk9exjo3f' FOREIGN KEY ('Artefact_id') REFERENCES 'ARTEFACT' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE 'Artefact_metadata' (
  'Artefact_id' bigint(20) NOT NULL,
  'metadata' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'metadata_KEY' varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY ('Artefact_id','metadata_KEY'),
  CONSTRAINT 'FK_odh03l78hgm1tfwo7jef8j6f5' FOREIGN KEY ('Artefact_id') REFERENCES 'ARTEFACT' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE 'ARTEFACTPAYLOAD' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'CREATED_AT' datetime DEFAULT NULL,
  'CREATEDBY' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'UPDATED_AT' datetime DEFAULT NULL,
  'UPDATEDBY' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'version' bigint(20) DEFAULT NULL,
  'FORMAT' int(11) NOT NULL,
  'PAYLOADNAME' varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  'PAYLOAD' longblob,
  'TYPE' int(11) NOT NULL,
  'domain_id' bigint(20) DEFAULT NULL,
  'subDomain_id' bigint(20) DEFAULT NULL,
  PRIMARY KEY ('id'),
  KEY 'FK_4emoh9mylny5uw6ccjue7dyqr' ('domain_id'),
  KEY 'FK_sn461s4kv34m9o1nowogh8mqa' ('subDomain_id'),
  CONSTRAINT 'FK_4emoh9mylny5uw6ccjue7dyqr' FOREIGN KEY ('domain_id') REFERENCES 'DOMAIN' ('id'),
  CONSTRAINT 'FK_sn461s4kv34m9o1nowogh8mqa' FOREIGN KEY ('subDomain_id') REFERENCES 'SUBDOMAIN' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE 'Artefact_tags' (
  'Artefact_id' bigint(20) NOT NULL,
  'tags' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  KEY 'FK_jqouh2st2yogmvcc8jtfo7i8c' ('Artefact_id'),
  CONSTRAINT 'FK_jqouh2st2yogmvcc8jtfo7i8c' FOREIGN KEY ('Artefact_id') REFERENCES 'ARTEFACT' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE 'DOMAIN' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'NAME' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'version' bigint(20) DEFAULT NULL,
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=5001 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE 'PROJECT' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'CREATED_AT' datetime DEFAULT NULL,
  'CREATEDBY' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'UPDATED_AT' datetime DEFAULT NULL,
  'UPDATEDBY' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'version' bigint(20) DEFAULT NULL,
  'PROJECTNAME' varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  'PRIVACYLEVEL' int(11) DEFAULT NULL,
  'domain_id' bigint(20) DEFAULT NULL,
  'subDomain_id' bigint(20) DEFAULT NULL,
  PRIMARY KEY ('id'),
  KEY 'FK_nyf4cgj93kdkwll97fteqi8nt' ('domain_id'),
  KEY 'FK_fw63g7j9dbrdc7mnomc3vxbaw' ('subDomain_id'),
  CONSTRAINT 'FK_fw63g7j9dbrdc7mnomc3vxbaw' FOREIGN KEY ('subDomain_id') REFERENCES 'SUBDOMAIN' ('id'),
  CONSTRAINT 'FK_nyf4cgj93kdkwll97fteqi8nt' FOREIGN KEY ('domain_id') REFERENCES 'DOMAIN' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE 'PROJECT_ARTEFACT' (
  'PROJECT_id' bigint(20) NOT NULL,
  'artefacts_id' bigint(20) NOT NULL,
  UNIQUE KEY 'UK_9yjc6eae5renwspj5jgl6a2nv' ('artefacts_id'),
  KEY 'FK_t7gxkvx0kd7o55skq9m6ugua9' ('PROJECT_id'),
  CONSTRAINT 'FK_9yjc6eae5renwspj5jgl6a2nv' FOREIGN KEY ('artefacts_id') REFERENCES 'ARTEFACT' ('id'),
  CONSTRAINT 'FK_t7gxkvx0kd7o55skq9m6ugua9' FOREIGN KEY ('PROJECT_id') REFERENCES 'PROJECT' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE 'SUBDOMAIN' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'NAME' varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  'version' bigint(20) DEFAULT NULL,
  'domainId' bigint(20) DEFAULT NULL,
  PRIMARY KEY ('id'),
  CONSTRAINT 'FK_etbfd11cscaqdpvoi9v4dyvd7' FOREIGN KEY ('domainId') REFERENCES 'DOMAIN' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
