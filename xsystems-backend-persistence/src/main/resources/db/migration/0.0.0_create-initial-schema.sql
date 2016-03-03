--
-- The persistence module of the backend of the xSystems web-application.
-- Copyright (C) 2015-2016  xSystems
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with this program.  If not, see <http://www.gnu.org/licenses/>.
--

CREATE TABLE FILE (ID BIGINT NOT NULL, TYPE VARCHAR(31), DESCRIPTION VARCHAR(255), NAME VARCHAR(255), URI BYTEA, USER_ID BIGINT, DTYPE VARCHAR(31), PRIMARY KEY (ID));
CREATE TABLE IMAGE (ID BIGINT NOT NULL, THUMBNAILURI BYTEA, PRIMARY KEY (ID));
CREATE TABLE "user" (ID BIGINT NOT NULL, EMAIL VARCHAR(255), PASSWORD VARCHAR(255), ROLE VARCHAR(255), PRIMARY KEY (ID));
ALTER TABLE IMAGE ADD CONSTRAINT FK_IMAGE_ID FOREIGN KEY (ID) REFERENCES FILE (ID);
CREATE SEQUENCE USER_ID_SEQ INCREMENT BY 50 START WITH 50;
CREATE SEQUENCE FILE_ID_SEQ INCREMENT BY 50 START WITH 50;
