/*
 *    Copyright (c) 2008-2011 Graham Allan
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.mutabilitydetector.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class PlainTextClassListToReportReader implements ClassListToReportCollector {

    private final BufferedReader reader;

    public PlainTextClassListToReportReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public Collection<String> classListToReport() {
        String line = null;
        Collection<String> classes = new HashSet<String>();
        try {
            while ((line = reader.readLine()) != null) {
                classes.add(line);
            }
        } catch (IOException e) {
            throw new ClassListException("I/O exception while reading class list.", e);
        }

        return classes;
    }

}