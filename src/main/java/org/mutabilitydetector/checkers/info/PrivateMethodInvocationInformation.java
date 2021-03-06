package org.mutabilitydetector.checkers.info;

/*
 * #%L
 * MutabilityDetector
 * %%
 * Copyright (C) 2008 - 2014 Graham Allan
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */



import static org.mutabilitydetector.locations.ClassIdentifier.forClass;

import java.util.HashMap;
import java.util.Map;

import org.mutabilitydetector.checkers.util.PrivateMethodInvocationAnalyser;
import org.mutabilitydetector.locations.Dotted;

public final class PrivateMethodInvocationInformation implements AnalysisInformation {

    private final Map<Dotted, PrivateMethodInvocationAnalyser> checkerCache = new HashMap<Dotted, PrivateMethodInvocationAnalyser>();
    private final InformationRetrievalRunner sessionCheckerRunner;

    public PrivateMethodInvocationInformation(InformationRetrievalRunner sessionCheckerRunner) {
        this.sessionCheckerRunner = sessionCheckerRunner;
    }

    public boolean isOnlyCalledFromConstructor(MethodIdentifier forMethod) {
        PrivateMethodInvocationAnalyser checker;
        if (checkerCache.containsKey(forMethod.dottedClassName())) {
            checker = checkerCache.get(forMethod.dottedClassName());
        } else {
            checker = new PrivateMethodInvocationAnalyser();
            sessionCheckerRunner.run(checker, forClass(forMethod.dottedClassName()));
            checkerCache.put(forMethod.dottedClassName(), checker);
        }
        return result(checker, forMethod);
    }

    private boolean result(PrivateMethodInvocationAnalyser checker, MethodIdentifier forMethod) {
        return checker.isPrivateMethodCalledOnlyFromConstructor(forMethod.methodDescriptor());
    }

}
