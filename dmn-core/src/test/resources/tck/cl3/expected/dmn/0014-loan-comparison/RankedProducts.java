
import java.util.*;
import java.util.stream.Collectors;

import static FinancialMetrics.FinancialMetrics;

@javax.annotation.Generated(value = {"decision.ftl", "RankedProducts"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "RankedProducts",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class RankedProducts extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "RankedProducts",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final Bankrates bankrates;

    public RankedProducts() {
        this(new Bankrates());
    }

    public RankedProducts(Bankrates bankrates) {
        this.bankrates = bankrates;
    }

    public type.TRankedProducts apply(String requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((requestedAmt != null ? number(requestedAmt) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'RankedProducts'", e);
            return null;
        }
    }

    public type.TRankedProducts apply(String requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((requestedAmt != null ? number(requestedAmt) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'RankedProducts'", e);
            return null;
        }
    }

    public type.TRankedProducts apply(java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(requestedAmt, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public type.TRankedProducts apply(java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("requestedAmt", requestedAmt);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Apply child decisions
            List<type.TLoanProduct> bankratesOutput = bankrates.apply(annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            type.TRankedProducts output_ = evaluate(bankratesOutput, requestedAmt, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'RankedProducts' evaluation", e);
            return null;
        }
    }

    private type.TRankedProducts evaluate(List<type.TLoanProduct> bankrates, java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        List<type.TMetric> metricsTable = bankrates.stream().map(i -> FinancialMetrics(i, requestedAmt, annotationSet_, eventListener_, externalExecutor_)).collect(Collectors.toList());
        List<type.TMetric> rankByRate = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {type.TMetric x = (type.TMetric)args[0]; type.TMetric y = (type.TMetric)args[1];return numericLessThan(((java.math.BigDecimal)x.getRate()), ((java.math.BigDecimal)y.getRate()));}});
        List<type.TMetric> rankByDownPmt = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {type.TMetric x = (type.TMetric)args[0]; type.TMetric y = (type.TMetric)args[1];return numericLessThan(((java.math.BigDecimal)x.getDownPmtAmt()), ((java.math.BigDecimal)y.getDownPmtAmt()));}});
        List<type.TMetric> rankByMonthlyPmt = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {type.TMetric x = (type.TMetric)args[0]; type.TMetric y = (type.TMetric)args[1];return numericLessThan(((java.math.BigDecimal)x.getPaymentAmt()), ((java.math.BigDecimal)y.getPaymentAmt()));}});
        List<type.TMetric> rankByEquityPct = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {type.TMetric x = (type.TMetric)args[0]; type.TMetric y = (type.TMetric)args[1];return numericGreaterThan(((java.math.BigDecimal)x.getEquity36moPct()), ((java.math.BigDecimal)y.getEquity36moPct()));}});
        type.TRankedProductsImpl rankedProducts = new type.TRankedProductsImpl();
        rankedProducts.setMetricsTable(metricsTable);
        rankedProducts.setRankByRate(rankByRate);
        rankedProducts.setRankByDownPmt(rankByDownPmt);
        rankedProducts.setRankByMonthlyPmt(rankByMonthlyPmt);
        rankedProducts.setRankByEquityPct(rankByEquityPct);
        return rankedProducts;
    }
}