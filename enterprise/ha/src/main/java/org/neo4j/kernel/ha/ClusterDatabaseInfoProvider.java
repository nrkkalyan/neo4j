/**
 * Copyright (c) 2002-2012 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.ha;

import org.neo4j.helpers.Functions;
import org.neo4j.helpers.collection.Iterables;
import org.neo4j.kernel.ha.cluster.member.ClusterMember;
import org.neo4j.kernel.ha.cluster.member.ClusterMembers;
import org.neo4j.management.ClusterDatabaseInfo;
import org.neo4j.management.ClusterMemberInfo;

public class ClusterDatabaseInfoProvider
{
    private final ClusterMembers members;

    public ClusterDatabaseInfoProvider( ClusterMembers members )
    {
        this.members = members;
    }

    public ClusterDatabaseInfo getInfo()
    {
        ClusterMember self = members.getSelf();
        if (self == null)
            return null;

        return new ClusterDatabaseInfo( new ClusterMemberInfo( self.getClusterUri().toString(), self.getHAUri() != null, true, self.getHARole(),
                Iterables.toArray(String.class, Iterables.map( Functions.TO_STRING, self.getRoleURIs() ))), 0, 0 );
    }
}
