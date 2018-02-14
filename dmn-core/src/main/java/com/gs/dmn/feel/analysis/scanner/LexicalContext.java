/**
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.feel.analysis.scanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LexicalContext {
    private List<String> names;
    private boolean ordered = false;

    public LexicalContext(String... names) {
        this.names =  new ArrayList<>();
        if (names != null) {
            for(String name: names) {
                this.names.add(name);
            }
        }
    }

    public LexicalContext(List<String> names) {
        if (names != null) {
            this.names = names;
        }
    }

    public LexicalContext(LexicalContext lexicalContext) {
        this(lexicalContext.names);
    }

    public void addName(String name) {
        this.ordered = false;
        this.names.add(name);
    }

    public List<String> orderedNames() {
        if (!ordered) {
            Collections.sort(this.names, (o1, o2) -> o2.length() - o1.length());
            this.ordered = true;
        }
        return this.names;
    }
}