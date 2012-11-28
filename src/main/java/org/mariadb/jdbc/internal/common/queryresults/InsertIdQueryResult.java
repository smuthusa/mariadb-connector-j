/*
MariaDB Client for Java

Copyright (c) 2012 Monty Program Ab. All Rights Reserved.

This library is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation; either version 2.1 of the License, or (at your option)
any later version.

This library is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
for more details.

You should have received a copy of the GNU Lesser General Public License along
with this library; if not, write to Monty Program Ab info@montyprogram.com.

This particular MariaDB C Connector Library APIClient for Java file is work
derived from a Drizzle-JDBC. Drizzle-JDBC file which is covered by subject to
the following copyright and notice provisions:

Copyright (c) 2009-2011, Marcus Eriksson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of the driver nor the names of its contributors may not be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS  AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
OF SUCH DAMAGE.
*/
package org.mariadb.jdbc.internal.common.queryresults;

import org.mariadb.jdbc.internal.common.ColumnInformation;
import org.mariadb.jdbc.internal.common.GeneratedIdValueObject;
import org.mariadb.jdbc.internal.common.ValueObject;
import org.mariadb.jdbc.internal.mysql.MySQLColumnInformation;
import org.mariadb.jdbc.internal.mysql.MySQLType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * . User: marcuse Date: Mar 9, 2009 Time: 8:34:44 PM
 */
public class InsertIdQueryResult extends SelectQueryResult {

    private final long insertId;
    private int rowPointer = 0;
    private final long rows;
    private static List<ColumnInformation> ci;


    public InsertIdQueryResult(final long insertId, final long rows) {
        this.insertId = insertId;
        this.rows = rows;
    }

    public ValueObject getValueObject(final int index) throws NoSuchColumnException {
        if (index != 0) {
            throw new NoSuchColumnException("No such column: " + index);
        }
        return new GeneratedIdValueObject(insertId);
    }

    public int getRows() {
        return (int) rows;
    }

    public void moveRowPointerTo(final int i) {

    }

    public int getRowPointer() {
        return rowPointer;
    }

    public boolean next() {
        return rowPointer++ < rows;
    }

    public synchronized List<ColumnInformation> getColumnInformation() {
        if (ci != null)
            return ci;

        MySQLColumnInformation.Builder b = new MySQLColumnInformation.Builder();
        MySQLColumnInformation info = b.db("").catalog("").charsetNumber((short) 0).decimals((byte) 0).name("insert_id" +
                "").originalName("insert_id").flags(EnumSet.noneOf(ColumnFlags.class)).
                originalTable("").table("").type(new MySQLType(MySQLType.Type.BIGINT)).build();

         ci =  new ArrayList<ColumnInformation>();
         ci.add(info);
         return ci;
    }


    public boolean isBeforeFirst() {
        return false;
    }
    public boolean  isAfterLast() {
      return rowPointer >= rows;
    }
    public void close() {

    }
}