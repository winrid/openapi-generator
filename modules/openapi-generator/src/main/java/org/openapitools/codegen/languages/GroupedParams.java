package org.openapitools.codegen.languages;

import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared helper for the FastComments grouped request shape used when
 * {@code useSingleRequestParameter} is enabled: required params and the request body
 * stay positional, while the remaining optional params are collected into a single
 * options object. The body is always treated as positional even if the spec marks it
 * optional, since a request body should never live in an options bag.
 *
 * <p>Populates three vendor extensions on the operation:
 * <ul>
 *   <li>{@code x-fc-positional-params} - required params plus the body</li>
 *   <li>{@code x-fc-grouped-params} - the remaining optional (non-body) params</li>
 *   <li>{@code x-fc-has-grouped} - whether {@code x-fc-grouped-params} is non-empty</li>
 * </ul>
 */
final class GroupedParams {
    private GroupedParams() {
    }

    static void split(CodegenOperation operation) {
        List<CodegenParameter> positional = new ArrayList<>(operation.requiredParams);
        List<CodegenParameter> grouped = new ArrayList<>();
        for (CodegenParameter p : operation.optionalParams) {
            if (p.isBodyParam) {
                positional.add(p);
            } else {
                grouped.add(p);
            }
        }
        operation.vendorExtensions.put("x-fc-positional-params", positional);
        operation.vendorExtensions.put("x-fc-grouped-params", grouped);
        operation.vendorExtensions.put("x-fc-has-grouped", !grouped.isEmpty());
    }
}
