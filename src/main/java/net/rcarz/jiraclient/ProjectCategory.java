/**
 * jira-client - a simple JIRA REST client
 * Copyright (c) 2013 Bob Carroll (bob.carroll@alum.rit.edu)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.rcarz.jiraclient;

import net.rcarz.jiraclient.util.JsonUtil;

import java.util.Map;

/**
 * Represents a project category.
 */
public class ProjectCategory extends Resource {

    public ProjectCategory() {
    }

    private String name = null;
    private String description = null;

    /**
     * Creates a category from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected ProjectCategory(RestClient restclient, Map json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(Map json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        description = Field.getString(map.get("description"));
        name = Field.getString(map.get("name"));
    }

    /**
     * Retrieves the given status record.
     *
     * @param restclient REST client instance
     * @param id Internal JIRA ID of the status
     *
     * @return a status instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static ProjectCategory get(RestClient restclient, String id)
            throws JiraException {

        Map result = null;

        try {
            String resultJson = restclient.get(getBaseUri() + "projectCategory/" + id);
            if (resultJson!=null) {
                result = JsonUtil.OBJECT_MAPPER.readValue(resultJson, Map.class);
            }
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve status " + id, ex);
        }

        if (result == null)
            throw new JiraException("JSON payload is malformed");

        return new ProjectCategory(restclient, result);
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}

