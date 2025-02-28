
import java.util.*;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.text.DecimalFormat;

// Representing financial market data
class MarketData {
    private Map<String, Double> interestRates;
    private Map<String, Double> currencyRates;
    private Map<String, Double> liquidityIndex;
    private LocalDate date;
    
    public MarketData() {
        this.interestRates = new HashMap<>();
        this.currencyRates = new HashMap<>();
        this.liquidityIndex = new HashMap<>();
        this.date = LocalDate.now();
        
        // Initialize with sample market data
        interestRates.put("OVERNIGHT", 4.25);
        interestRates.put("1MONTH", 4.35);
        interestRates.put("3MONTH", 4.45);
        interestRates.put("6MONTH", 4.50);
        interestRates.put("1YEAR", 4.75);
        
        currencyRates.put("USD/IDR", 15750.0);
        currencyRates.put("EUR/IDR", 17000.0);
        currencyRates.put("JPY/IDR", 105.0);
        
        liquidityIndex.put("MARKET_LIQUIDITY", 0.85);  // Market liquidity index (0-1)
    }
    
    // Simulate market data changes
    public void updateMarketData() {
        // Simulate interest rate fluctuations
        for (String tenor : interestRates.keySet()) {
            double currentRate = interestRates.get(tenor);
            double change = ThreadLocalRandom.current().nextDouble(-0.15, 0.15);
            interestRates.put(tenor, Math.max(0, currentRate + change));
        }
        
        // Simulate currency rate fluctuations
        for (String pair : currencyRates.keySet()) {
            double currentRate = currencyRates.get(pair);
            double percentChange = ThreadLocalRandom.current().nextDouble(-0.01, 0.01);
            currencyRates.put(pair, currentRate * (1 + percentChange));
        }
        
        // Update liquidity index
        double currentLiquidity = liquidityIndex.get("MARKET_LIQUIDITY");
        double change = ThreadLocalRandom.current().nextDouble(-0.05, 0.05);
        liquidityIndex.put("MARKET_LIQUIDITY", Math.min(1.0, Math.max(0.5, currentLiquidity + change)));
        
        // Update date
        this.date = this.date.plusDays(1);
    }
    
    public double getInterestRate(String tenor) {
        return interestRates.getOrDefault(tenor, 0.0);
    }
    
    public double getCurrencyRate(String pair) {
        return currencyRates.getOrDefault(pair, 0.0);
    }
    
    public double getLiquidityIndex() {
        return liquidityIndex.get("MARKET_LIQUIDITY");
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void displayMarketData() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("\n===== MARKET DATA: " + date + " =====");
        System.out.println("INTEREST RATES:");
        for (Map.Entry<String, Double> entry : interestRates.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + df.format(entry.getValue()) + "%");
        }
        
        System.out.println("\nCURRENCY RATES:");
        for (Map.Entry<String, Double> entry : currencyRates.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + df.format(entry.getValue()));
        }
        
        System.out.println("\nMARKET LIQUIDITY INDEX: " + df.format(liquidityIndex.get("MARKET_LIQUIDITY") * 100) + "%");
    }
}

// Enhanced CashFlow class
class CashFlow {
    private double yearlyRevenue;
    private double yearlyOperatingExpenses;
    private double yearlyCapEx;
    private List<CashFlowEvent> scheduledCashFlows;

    public CashFlow(double yearlyRevenue, double yearlyOperatingExpenses, double yearlyCapEx) {
        this.yearlyRevenue = yearlyRevenue;
        this.yearlyOperatingExpenses = yearlyOperatingExpenses;
        this.yearlyCapEx = yearlyCapEx;
        this.scheduledCashFlows = new ArrayList<>();
    }

    public double getYearlyRevenue() {
        return yearlyRevenue;
    }

    public double getYearlyOperatingExpenses() {
        return yearlyOperatingExpenses;
    }

    public double getYearlyCapEx() {
        return yearlyCapEx;
    }
    
    public void addCashFlowEvent(CashFlowEvent event) {
        scheduledCashFlows.add(event);
    }
    
    public List<CashFlowEvent> getScheduledCashFlows() {
        return scheduledCashFlows;
    }
}

// Representing scheduled cash flow events
class CashFlowEvent {
    private String description;
    private LocalDate date;
    private double amount;
    private boolean isInflow;
    private boolean isRecurring;
    private int recurringInterval; // in days
    
    public CashFlowEvent(String description, LocalDate date, double amount, boolean isInflow, 
                        boolean isRecurring, int recurringInterval) {
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.isInflow = isInflow;
        this.isRecurring = isRecurring;
        this.recurringInterval = recurringInterval;
    }
    
    public String getDescription() {
        return description;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public boolean isInflow() {
        return isInflow;
    }
    
    public boolean isRecurring() {
        return isRecurring;
    }
    
    public int getRecurringInterval() {
        return recurringInterval;
    }
    
    public CashFlowEvent getNextRecurrence() {
        if (!isRecurring) return null;
        
        return new CashFlowEvent(
            description,
            date.plusDays(recurringInterval),
            amount,
            isInflow,
            true,
            recurringInterval
        );
    }
}

// Asset class for representing different types of assets in portfolio
class Asset {
    private String name;
    private String type; // cash, bonds, equities, etc.
    private double amount;
    private String currency;
    private double interestRate; // for interest-bearing assets
    private LocalDate maturityDate; // for time-bound assets
    private double liquidityRating; // 0-1, how quickly it can be converted to cash
    
    public Asset(String name, String type, double amount, String currency, 
                double interestRate, LocalDate maturityDate, double liquidityRating) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.interestRate = interestRate;
        this.maturityDate = maturityDate;
        this.liquidityRating = liquidityRating;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
    public LocalDate getMaturityDate() {
        return maturityDate;
    }
    
    public double getLiquidityRating() {
        return liquidityRating;
    }
}

// Portfolio to manage assets
class TreasuryPortfolio {
    private List<Asset> assets;
    private double totalValue;
    private double cashReserve;
    private String baseCurrency;
    
    public TreasuryPortfolio(String baseCurrency, double initialCash) {
        this.assets = new ArrayList<>();
        this.baseCurrency = baseCurrency;
        this.cashReserve = initialCash;
        this.totalValue = initialCash;
        
        // Add initial cash as an asset
        assets.add(new Asset("Cash Reserve", "CASH", initialCash, baseCurrency, 0.0, null, 1.0));
    }
    
    public void addAsset(Asset asset) {
        assets.add(asset);
        updateTotalValue();
    }
    
    public List<Asset> getAssets() {
        return assets;
    }
    
    public double getTotalValue() {
        return totalValue;
    }
    
    public void updateTotalValue() {
        totalValue = assets.stream().mapToDouble(Asset::getAmount).sum();
    }
    
    public double getCashReserve() {
        return assets.stream()
            .filter(a -> a.getType().equals("CASH") && a.getCurrency().equals(baseCurrency))
            .mapToDouble(Asset::getAmount)
            .sum();
    }
    
    public void displayPortfolio() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("\n===== TREASURY PORTFOLIO =====");
        System.out.println("BASE CURRENCY: " + baseCurrency);
        System.out.println("TOTAL VALUE: " + df.format(totalValue) + " " + baseCurrency);
        System.out.println("CASH RESERVE: " + df.format(getCashReserve()) + " " + baseCurrency);
        
        System.out.println("\nASSETS:");
        for (Asset asset : assets) {
            System.out.println("  " + asset.getName() + " (" + asset.getType() + ")");
            System.out.println("    Amount: " + df.format(asset.getAmount()) + " " + asset.getCurrency());
            System.out.println("    Interest Rate: " + (asset.getInterestRate() > 0 ? df.format(asset.getInterestRate()) + "%" : "N/A"));
            System.out.println("    Maturity Date: " + (asset.getMaturityDate() != null ? asset.getMaturityDate() : "N/A"));
            System.out.println("    Liquidity Rating: " + df.format(asset.getLiquidityRating() * 100) + "%");
            System.out.println();
        }
    }
    
    // Calculate liquidity ratio (proportion of assets that can be quickly converted to cash)
    public double calculateLiquidityRatio() {
        double liquidAssets = assets.stream()
            .filter(a -> a.getLiquidityRating() >= 0.8)
            .mapToDouble(Asset::getAmount)
            .sum();
            
        return liquidAssets / totalValue;
    }
}

// Risk analysis with Monte Carlo simulation
class RiskAnalysis {
    private TreasuryPortfolio portfolio;
    private MarketData marketData;
    private int simulationRuns;
    
    public RiskAnalysis(TreasuryPortfolio portfolio, MarketData marketData, int simulationRuns) {
        this.portfolio = portfolio;
        this.marketData = marketData;
        this.simulationRuns = simulationRuns;
    }
    
    // Monte Carlo simulation for liquidity risk
    public Map<String, Double> runLiquidityRiskSimulation(int days) {
        double[] outcomes = new double[simulationRuns];
        Random rand = new Random();
        
        for (int i = 0; i < simulationRuns; i++) {
            // Clone current portfolio state
            double currentCash = portfolio.getCashReserve();
            double marketLiquidity = marketData.getLiquidityIndex();
            
            // Simulate daily changes
            for (int day = 0; day < days; day++) {
                // Random cash outflow based on operating expenses
                double dailyExpense = portfolio.getTotalValue() * 0.001 * (1 + rand.nextGaussian() * 0.3);
                
                // Random cash inflow based on revenue
                double dailyRevenue = portfolio.getTotalValue() * 0.0012 * (1 + rand.nextGaussian() * 0.25);
                
                // Adjust based on market liquidity
                double marketEffect = (marketLiquidity - 0.5) * 2.0 * portfolio.getTotalValue() * 0.0002;
                
                // Update cash position
                currentCash = currentCash + dailyRevenue - dailyExpense + marketEffect;
                
                // Randomly change market liquidity
                marketLiquidity = Math.min(1.0, Math.max(0.5, marketLiquidity + rand.nextGaussian() * 0.05));
            }
            
            outcomes[i] = currentCash;
        }
        
        // Analyze results
        Arrays.sort(outcomes);
        Map<String, Double> results = new HashMap<>();
        results.put("WORST_CASE", outcomes[0]);
        results.put("PERCENTILE_5", outcomes[(int)(simulationRuns * 0.05)]);
        results.put("MEAN", Arrays.stream(outcomes).average().orElse(0));
        results.put("PERCENTILE_95", outcomes[(int)(simulationRuns * 0.95)]);
        results.put("BEST_CASE", outcomes[simulationRuns - 1]);
        
        return results;
    }
    
    // Interest rate risk assessment
    public Map<String, Double> assessInterestRateRisk(double rateChangePercent) {
        Map<String, Double> impact = new HashMap<>();
        double totalImpact = 0;
        
        for (Asset asset : portfolio.getAssets()) {
            if (asset.getMaturityDate() != null) {
                // Calculate duration (simplified)
                LocalDate now = LocalDate.now();
                long daysToMaturity = now.until(asset.getMaturityDate()).getDays();
                double duration = daysToMaturity / 365.0;
                
                // Calculate impact
                double assetImpact = -asset.getAmount() * duration * (rateChangePercent/100);
                impact.put(asset.getName(), assetImpact);
                totalImpact += assetImpact;
            }
        }
        
        impact.put("TOTAL_IMPACT", totalImpact);
        return impact;
    }
    
    public void displayRiskAnalysis(int forecastDays) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("\n===== RISK ANALYSIS =====");
        
        Map<String, Double> liquidityRisk = runLiquidityRiskSimulation(forecastDays);
        System.out.println("CASH POSITION AFTER " + forecastDays + " DAYS (Monte Carlo with " + simulationRuns + " simulations):");
        System.out.println("  Worst Case: " + df.format(liquidityRisk.get("WORST_CASE")));
        System.out.println("  5th Percentile: " + df.format(liquidityRisk.get("PERCENTILE_5")));
        System.out.println("  Average: " + df.format(liquidityRisk.get("MEAN")));
        System.out.println("  95th Percentile: " + df.format(liquidityRisk.get("PERCENTILE_95")));
        System.out.println("  Best Case: " + df.format(liquidityRisk.get("BEST_CASE")));
        
        double cashReserveRequired = portfolio.getTotalValue() * 0.2;
        System.out.println("\nCASH RESERVE REQUIREMENT (" + df.format(cashReserveRequired) + "):");
        double probability = calculateProbabilityBelowTarget(liquidityRisk.get("WORST_CASE"), 
                                                            liquidityRisk.get("BEST_CASE"), 
                                                            cashReserveRequired);
        System.out.println("  Probability of insufficient liquidity: " + df.format(probability * 100) + "%");
        
        // Interest rate risk
        System.out.println("\nINTEREST RATE RISK:");
        System.out.println("  Impact of 1% rate increase: " + df.format(assessInterestRateRisk(1).get("TOTAL_IMPACT")));
        System.out.println("  Impact of 1% rate decrease: " + df.format(assessInterestRateRisk(-1).get("TOTAL_IMPACT")));
    }
    
    private double calculateProbabilityBelowTarget(double min, double max, double target) {
        if (target <= min) return 0.0;
        if (target >= max) return 1.0;
        
        // Simple linear approximation
        return (target - min) / (max - min);
    }
}

// Cash optimization strategy
class CashOptimizationStrategy {
    private TreasuryPortfolio portfolio;
    private MarketData marketData;
    
    public CashOptimizationStrategy(TreasuryPortfolio portfolio, MarketData marketData) {
        this.portfolio = portfolio;
        this.marketData = marketData;
    }
    
    public void optimizeCashHoldings() {
        double cash = portfolio.getCashReserve();
        double totalValue = portfolio.getTotalValue();
        double liquidityRatio = portfolio.calculateLiquidityRatio();
        double marketLiquidity = marketData.getLiquidityIndex();
        
        // Recommended cash levels based on market conditions
        double recommendedMinCash = totalValue * 0.15 * (2 - marketLiquidity);
        double recommendedMaxCash = totalValue * 0.3 * (2 - marketLiquidity);
        
        // Decision making
        String action = "MAINTAIN";
        double amount = 0;
        
        if (cash < recommendedMinCash) {
            action = "INCREASE_CASH";
            amount = recommendedMinCash - cash;
        } else if (cash > recommendedMaxCash) {
            action = "DECREASE_CASH";
            amount = cash - recommendedMaxCash;
        }
        
        // Implement strategy
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("\n===== CASH OPTIMIZATION STRATEGY =====");
        System.out.println("CURRENT CASH: " + df.format(cash));
        System.out.println("RECOMMENDED CASH RANGE: " + df.format(recommendedMinCash) + " - " + df.format(recommendedMaxCash));
        System.out.println("RECOMMENDATION: " + action);
        
        if (!action.equals("MAINTAIN")) {
            System.out.println("AMOUNT: " + df.format(amount));
            
            if (action.equals("INCREASE_CASH")) {
                System.out.println("SUGGESTED ACTIONS:");
                System.out.println("1. Liquidate short-term investments (around " + df.format(amount) + ")");
                System.out.println("2. Consider short-term borrowing from money markets");
                System.out.println("3. Accelerate accounts receivable collection");
            } else {
                System.out.println("SUGGESTED ACTIONS:");
                System.out.println("1. Invest excess cash in short-term instruments (around " + df.format(amount) + ")");
                System.out.println("2. Consider early payment of liabilities to reduce interest expense");
                System.out.println("3. Explore higher-yield investment opportunities");
            }
        } else {
            System.out.println("Current cash levels are within optimal range.");
        }
    }
    
    // Get best investment opportunities based on current market conditions
    public List<String> getInvestmentRecommendations() {
        List<String> recommendations = new ArrayList<>();
        double overnightRate = marketData.getInterestRate("OVERNIGHT");
        double oneMonthRate = marketData.getInterestRate("1MONTH");
        double threeMonthRate = marketData.getInterestRate("3MONTH");
        double sixMonthRate = marketData.getInterestRate("6MONTH");
        double oneYearRate = marketData.getInterestRate("1YEAR");
        
        // Simple yield curve analysis
        boolean invertedYieldCurve = oneYearRate < overnightRate;
        boolean steepYieldCurve = (oneYearRate - overnightRate) > 1.0;
        
        if (invertedYieldCurve) {
            recommendations.add("CAUTION: Inverted yield curve detected. Consider holding more cash and shortening investment duration.");
            recommendations.add("Overnight deposits offer good value at " + overnightRate + "%");
        } else if (steepYieldCurve) {
            recommendations.add("Steep yield curve detected. Consider extending investment duration to capture higher rates.");
            recommendations.add("1-year instruments offer strong value at " + oneYearRate + "%");
        } else {
            // Normal yield curve
            // Find the "sweet spot" on the yield curve
            double[] rates = {overnightRate, oneMonthRate, threeMonthRate, sixMonthRate, oneYearRate};
            String[] tenors = {"OVERNIGHT", "1MONTH", "3MONTH", "6MONTH", "1YEAR"};
            
            double maxYieldPerDuration = 0;
            String bestTenor = "";
            
            for (int i = 0; i < rates.length; i++) {
                double durationInDays = 0;
                switch (tenors[i]) {
                    case "OVERNIGHT": durationInDays = 1; break;
                    case "1MONTH": durationInDays = 30; break;
                    case "3MONTH": durationInDays = 90; break;
                    case "6MONTH": durationInDays = 180; break;
                    case "1YEAR": durationInDays = 365; break;
                }
                
                double yieldPerDay = rates[i] / durationInDays;
                if (yieldPerDay > maxYieldPerDuration) {
                    maxYieldPerDuration = yieldPerDay;
                    bestTenor = tenors[i];
                }
            }
            
            recommendations.add("Based on current yield curve, " + bestTenor + " tenor offers the best value.");
        }
        
        return recommendations;
    }
}

// User login interface
interface UserType {}

class User implements UserType {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String getRole() {
        return role;
    }

    public void displayWelcomeMessage() {
        System.out.println("\n-----Selamat Datang " + username + "-----\n");
    }
}

class TreasuryAnalyst extends User {
    public TreasuryAnalyst(String username, String password) {
        super(username, password, "Treasury Analyst");
    }

    @Override
    public void displayWelcomeMessage() {
        System.out.println("\n-----Selamat Datang Treasury Analyst: " + getUsername() + "-----\n");
    }
}

public class TreasuryLiquiditySimulator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean izinLogin = false;
        
        // Create users
        TreasuryAnalyst analyst = new TreasuryAnalyst("analyst", "password");
        User admin = new User("admin", "admin", "Administrator");
        
        User currentUser = null;
        
        while (!izinLogin) {
            System.out.println("===== TREASURY LIQUIDITY SIMULATOR LOGIN =====\n");
            System.out.print("Masukkan username (analyst/admin): ");
            String username = sc.nextLine();
            System.out.print("Masukkan password: ");
            String password = sc.nextLine();

            if (username.equals(analyst.getUsername()) && password.equals(analyst.getPassword())) {
                currentUser = analyst;
                izinLogin = true;
            } else if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                currentUser = admin;
                izinLogin = true;
            } else {
                System.out.println("\n-----Warning!-----\nUsername dan password anda salah\n");
                continue;
            }
            
            // Method Reference for welcome message
            Runnable welcomeMessage = currentUser::displayWelcomeMessage;
            welcomeMessage.run();
            
            runTreasurySimulator(currentUser);
        }

        sc.close();
    }

    public static void runTreasurySimulator(User user) {
        Scanner scanner = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#,##0.00");

        try {
            System.out.println("Initializing Treasury Liquidity Simulator...\n");
            
            // Initialize market data
            MarketData marketData = new MarketData();
            
            // Initialize portfolio
            System.out.print("Enter initial cash reserve (IDR): ");
            double initialCash = Double.parseDouble(scanner.nextLine());
            TreasuryPortfolio portfolio = new TreasuryPortfolio("IDR", initialCash);
            
            // Add some sample assets
            System.out.println("\nAdding default assets to portfolio...");
            portfolio.addAsset(new Asset("Government Bonds", "BONDS", 500000000.0, "IDR", 5.25, 
                                        LocalDate.now().plusDays(365), 0.7));
            portfolio.addAsset(new Asset("Money Market Deposit", "MM_DEPOSIT", 250000000.0, "IDR", 4.30, 
                                        LocalDate.now().plusDays(30), 0.9));
            portfolio.addAsset(new Asset("USD Cash", "CASH", 50000.0, "USD", 0.0, null, 1.0));
            
            // Create cash flow
            CashFlow cashFlow = new CashFlow(5000000000.0, 4200000000.0, 500000000.0);
            
            // Add some scheduled cash flows
            cashFlow.addCashFlowEvent(new CashFlowEvent("Quarterly Tax Payment", 
                                    LocalDate.now().plusDays(20), 120000000.0, false, false, 0));
            cashFlow.addCashFlowEvent(new CashFlowEvent("Monthly Revenue Collection", 
                                    LocalDate.now().plusDays(30), 420000000.0, true, true, 30));
            cashFlow.addCashFlowEvent(new CashFlowEvent("Bond Interest Payment", 
                                    LocalDate.now().plusDays(45), 26250000.0, true, false, 0));
            
            // Initialize risk analysis with 1000 simulation runs
            RiskAnalysis riskAnalysis = new RiskAnalysis(portfolio, marketData, 1000);
            
            // Initialize cash optimization
            CashOptimizationStrategy cashOptimization = new CashOptimizationStrategy(portfolio, marketData);
            
            boolean exit = false;
            LocalDate simulationDate = LocalDate.now();
            
            while (!exit) {
                System.out.println("\n===== TREASURY LIQUIDITY SIMULATOR - " + simulationDate + " =====");
                System.out.println("1. View Portfolio");
                System.out.println("2. View Market Data");
                System.out.println("3. Run Risk Analysis");
                System.out.println("4. Get Cash Optimization Strategy");
                System.out.println("5. Add Asset");
                System.out.println("6. Add Cash Flow Event");
                System.out.println("7. Simulate Next Day");
                System.out.println("8. View Investment Recommendations");
                System.out.println("9. Exit");
                
                System.out.print("\nSelect option: ");
                int option = Integer.parseInt(scanner.nextLine());
                
                switch (option) {
                    case 1:
                        portfolio.displayPortfolio();
                        break;
                    case 2:
                        marketData.displayMarketData();
                        break;
                    case 3:
                        System.out.print("Enter forecast period (days): ");
                        int days = Integer.parseInt(scanner.nextLine());
                        riskAnalysis.displayRiskAnalysis(days);
                        break;
                    case 4:
                        cashOptimization.optimizeCashHoldings();
                        break;
                    case 5:
                        addNewAsset(scanner, portfolio);
                        break;
                    case 6:
                        addCashFlowEvent(scanner, cashFlow);
                        break;
                    case 7:
                        simulateNextDay(marketData, portfolio, cashFlow, simulationDate);
                        simulationDate = simulationDate.plusDays(1);
                        break;
                    case 8:
                        System.out.println("\n===== INVESTMENT RECOMMENDATIONS =====");
                        List<String> recommendations = cashOptimization.getInvestmentRecommendations();
                        for (String rec : recommendations) {
                            System.out.println("- " + rec);
                        }
                        break;
                    case 9:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values.");
        } finally {
            scanner.close();
        }
    }
    
    private static void addNewAsset(Scanner scanner, TreasuryPortfolio portfolio) {
        try {
            System.out.println("\n===== ADD NEW ASSET =====");
            System.out.print("Asset Name: ");
            String name = scanner.nextLine();
            
            System.out.println("Asset Type (CASH, BONDS, DEPOSIT, EQUITY): ");
            String type = scanner.nextLine().toUpperCase();
            
            System.out.print("Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Currency (IDR, USD, etc.): ");
            String currency = scanner.nextLine().toUpperCase();
            
            System.out.print("Interest Rate (%): ");
            double interestRate = Double.parseDouble(scanner.nextLine());
            
            LocalDate maturityDate = null;
            if (!type.equals("CASH") && !type.equals("EQUITY")) {
                System.out.print("Maturity Date (days from now): ");
                int daysToMaturity = Integer.parseInt(scanner.nextLine());
                maturityDate = LocalDate.now().plusDays(daysToMaturity);
            }
            
            System.out.print("Liquidity Rating (0-1, where 1 is most liquid): ");
            double liquidityRating = Double.parseDouble(scanner.nextLine());
            
            Asset newAsset = new Asset(name, type, amount, currency, interestRate, maturityDate, liquidityRating);
            portfolio.addAsset(newAsset);
            
            System.out.println("Asset added successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values correctly.");
        }
    }
    
    private static void addCashFlowEvent(Scanner scanner, CashFlow cashFlow) {
        try {
            System.out.println("\n===== ADD CASH FLOW EVENT =====");
            System.out.print("Event Description: ");
            String description = scanner.nextLine();
            
            System.out.print("Days from now: ");
            int daysFromNow = Integer.parseInt(scanner.nextLine());
            LocalDate date = LocalDate.now().plusDays(daysFromNow);
            
            System.out.print("Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Is this an inflow? (true/false): ");
            boolean isInflow = Boolean.parseBoolean(scanner.nextLine());
            
            System.out.print("Is this a recurring event? (true/false): ");
            boolean isRecurring = Boolean.parseBoolean(scanner.nextLine());
            
            int recurringInterval = 0;
            if (isRecurring) {
                System.out.print("Recurring interval (days): ");
                recurringInterval = Integer.parseInt(scanner.nextLine());
            }
            
            CashFlowEvent event = new CashFlowEvent(description, date, amount, isInflow, isRecurring, recurringInterval);
            cashFlow.addCashFlowEvent(event);
            
            System.out.println("Cash flow event added successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values correctly.");
        }
    }
    
    private static void simulateNextDay(MarketData marketData, TreasuryPortfolio portfolio, CashFlow cashFlow, LocalDate currentDate) {
        System.out.println("\n===== SIMULATING NEXT DAY =====");
        
        // Update market data
        marketData.updateMarketData();
        
        // Apply interest to interest-bearing assets
        for (Asset asset : portfolio.getAssets()) {
            if (asset.getInterestRate() > 0) {
                double dailyInterest = asset.getAmount() * (asset.getInterestRate() / 100 / 365);
                asset.setAmount(asset.getAmount() + dailyInterest);
                System.out.println("Interest accrued on " + asset.getName() + ": " + new DecimalFormat("#,##0.00").format(dailyInterest));
            }
            
            // Check for maturing assets
            if (asset.getMaturityDate() != null && asset.getMaturityDate().equals(currentDate.plusDays(1))) {
                System.out.println("ALERT: " + asset.getName() + " matures tomorrow!");
            }
        }
        
        // Process scheduled cash flows
        LocalDate nextDay = currentDate.plusDays(1);
        List<CashFlowEvent> events = cashFlow.getScheduledCashFlows();
        List<CashFlowEvent> newEvents = new ArrayList<>();
        List<CashFlowEvent> processedEvents = new ArrayList<>();
        
        for (CashFlowEvent event : events) {
            if (event.getDate().equals(nextDay)) {
                // Apply cash flow
                for (Asset asset : portfolio.getAssets()) {
                    if (asset.getType().equals("CASH") && asset.getCurrency().equals("IDR")) {
                        double newAmount = asset.getAmount() + (event.isInflow() ? event.getAmount() : -event.getAmount());
                        asset.setAmount(newAmount);
                        System.out.println("Cash flow applied: " + event.getDescription() + 
                                         " - " + (event.isInflow() ? "+" : "-") + 
                                         new DecimalFormat("#,##0.00").format(event.getAmount()));
                        
                        // Create next recurrence if applicable
                        if (event.isRecurring()) {
                            newEvents.add(event.getNextRecurrence());
                        }
                        
                        processedEvents.add(event);
                        break;
                    }
                }
            }
        }
        
        // Remove processed events and add new recurrences
        events.removeAll(processedEvents);
        events.addAll(newEvents);
        
        // Update portfolio total value
        portfolio.updateTotalValue();
        
        System.out.println("Day simulated successfully!");
    }
}

// Enhanced investment strategy using modern portfolio theory
class InvestmentStrategy {
    private TreasuryPortfolio portfolio;
    private MarketData marketData;
    private double riskTolerance; // 0-1 scale, where 0 is risk-averse and 1 is risk-seeking
    
    public InvestmentStrategy(TreasuryPortfolio portfolio, MarketData marketData, double riskTolerance) {
        this.portfolio = portfolio;
        this.marketData = marketData;
        this.riskTolerance = riskTolerance;
    }
    
    public Map<String, Double> generateOptimalAllocation() {
        Map<String, Double> allocation = new HashMap<>();
        double cashReserve = portfolio.getCashReserve();
        double totalValue = portfolio.getTotalValue();
        double marketLiquidity = marketData.getLiquidityIndex();
        
        // Base allocations adjusted by risk tolerance and market conditions
        double cashAllocation = Math.max(0.15, 0.3 - (riskTolerance * 0.2) - (marketLiquidity * 0.1));
        double shortTermBondAllocation = 0.3 - (riskTolerance * 0.15);
        double mediumTermBondAllocation = 0.2 + (riskTolerance * 0.05);
        double longTermBondAllocation = 0.1 + (riskTolerance * 0.15);
        double alternativeAllocation = 0.1 + (riskTolerance * 0.15);
        
        // Yield curve analysis for bond allocations
        double oneMonthRate = marketData.getInterestRate("1MONTH");
        double threeMonthRate = marketData.getInterestRate("3MONTH");
        double oneYearRate = marketData.getInterestRate("1YEAR");
        
        // Steepness of yield curve
        double slopeShort = (threeMonthRate - oneMonthRate) / 2.0; // % per month
        double slopeLong = (oneYearRate - threeMonthRate) / 9.0;  // % per month
        
        // Adjust allocations based on yield curve
        if (slopeShort < 0 && slopeLong < 0) {
            // Inverted yield curve - recession warning
            cashAllocation += 0.1;
            shortTermBondAllocation += 0.05;
            mediumTermBondAllocation -= 0.05;
            longTermBondAllocation -= 0.1;
        } else if (slopeShort > 0.2 || slopeLong > 0.2) {
            // Steep yield curve - economic expansion expected
            cashAllocation -= 0.05;
            shortTermBondAllocation -= 0.05;
            longTermBondAllocation += 0.1;
        }
        
        // Normalize allocations to ensure they sum to 1
        double totalAllocation = cashAllocation + shortTermBondAllocation + 
                               mediumTermBondAllocation + longTermBondAllocation + alternativeAllocation;
                               
        allocation.put("CASH", cashAllocation / totalAllocation);
        allocation.put("SHORT_TERM_BONDS", shortTermBondAllocation / totalAllocation);
        allocation.put("MEDIUM_TERM_BONDS", mediumTermBondAllocation / totalAllocation);
        allocation.put("LONG_TERM_BONDS", longTermBondAllocation / totalAllocation);
        allocation.put("ALTERNATIVES", alternativeAllocation / totalAllocation);
        
        allocation.put("CASH_AMOUNT", allocation.get("CASH") * totalValue);
        allocation.put("SHORT_TERM_BONDS_AMOUNT", allocation.get("SHORT_TERM_BONDS") * totalValue);
        allocation.put("MEDIUM_TERM_BONDS_AMOUNT", allocation.get("MEDIUM_TERM_BONDS") * totalValue);
        allocation.put("LONG_TERM_BONDS_AMOUNT", allocation.get("LONG_TERM_BONDS") * totalValue);
        allocation.put("ALTERNATIVES_AMOUNT", allocation.get("ALTERNATIVES") * totalValue);
        
        return allocation;
    }
    
    public void displayStrategy() {
        Map<String, Double> allocation = generateOptimalAllocation();
        DecimalFormat pctFormat = new DecimalFormat("0.0%");
        DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
        
        System.out.println("\n===== OPTIMAL PORTFOLIO ALLOCATION =====");
        System.out.println("Risk Tolerance Level: " + (riskTolerance * 10) + "/10");
        System.out.println("Current Market Liquidity: " + pctFormat.format(marketData.getLiquidityIndex()));
        
        System.out.println("\nRECOMMENDED ASSET ALLOCATION:");
        System.out.println("Cash: " + pctFormat.format(allocation.get("CASH")) + 
                         " (IDR " + moneyFormat.format(allocation.get("CASH_AMOUNT")) + ")");
        System.out.println("Short-term Bonds (< 1 year): " + pctFormat.format(allocation.get("SHORT_TERM_BONDS")) + 
                         " (IDR " + moneyFormat.format(allocation.get("SHORT_TERM_BONDS_AMOUNT")) + ")");
        System.out.println("Medium-term Bonds (1-3 years): " + pctFormat.format(allocation.get("MEDIUM_TERM_BONDS")) + 
                         " (IDR " + moneyFormat.format(allocation.get("MEDIUM_TERM_BONDS_AMOUNT")) + ")");
        System.out.println("Long-term Bonds (> 3 years): " + pctFormat.format(allocation.get("LONG_TERM_BONDS")) + 
                         " (IDR " + moneyFormat.format(allocation.get("LONG_TERM_BONDS_AMOUNT")) + ")");
        System.out.println("Alternative Investments: " + pctFormat.format(allocation.get("ALTERNATIVES")) + 
                         " (IDR " + moneyFormat.format(allocation.get("ALTERNATIVES_AMOUNT")) + ")");
        
        // Current vs recommended cash position
        double currentCash = portfolio.getCashReserve();
        double recommendedCash = allocation.get("CASH_AMOUNT");
        System.out.println("\nCASH POSITION ADJUSTMENT:");
        if (Math.abs(currentCash - recommendedCash) > 100000) {
            if (currentCash < recommendedCash) {
                System.out.println("RECOMMENDATION: Increase cash position by IDR " + 
                                 moneyFormat.format(recommendedCash - currentCash));
                System.out.println("Consider liquidating some short-term investments or arranging short-term financing.");
            } else {
                System.out.println("RECOMMENDATION: Decrease cash position by IDR " + 
                                 moneyFormat.format(currentCash - recommendedCash));
                System.out.println("Consider investing excess cash according to the recommended allocation above.");
            }
        } else {
            System.out.println("Current cash position is within optimal range.");
        }
        
        // Additional strategic recommendations
        System.out.println("\nSTRATEGIC RECOMMENDATIONS:");
        if (marketData.getLiquidityIndex() < 0.6) {
            System.out.println("⚠️ Market liquidity is low. Prioritize defensive positions and maintain higher cash reserves.");
        }
        
        // Interest rate trend analysis
        double overnightRate = marketData.getInterestRate("OVERNIGHT");
        double oneYearRate = marketData.getInterestRate("1YEAR");
        if (oneYearRate < overnightRate) {
            System.out.println("⚠️ Inverted yield curve detected. Consider defensive positioning and prepare for potential economic slowdown.");
        } else if ((oneYearRate - overnightRate) > 1.5) {
            System.out.println("📈 Steep yield curve detected. Consider extending duration to capture higher yields.");
        }
    }
}

// Currency risk management class
class CurrencyRiskManager {
    private TreasuryPortfolio portfolio;
    private MarketData marketData;
    
    public CurrencyRiskManager(TreasuryPortfolio portfolio, MarketData marketData) {
        this.portfolio = portfolio;
        this.marketData = marketData;
    }
    
    public Map<String, Double> calculateCurrencyExposure() {
        Map<String, Double> exposure = new HashMap<>();
        double totalValue = portfolio.getTotalValue();
        
        // Calculate exposure by currency
        for (Asset asset : portfolio.getAssets()) {
            String currency = asset.getCurrency();
            double amount = asset.getAmount();
            
            // Convert to base currency (IDR) if needed
            if (!currency.equals("IDR")) {
                String pair = currency + "/IDR";
                double rate = marketData.getCurrencyRate(pair);
                amount *= rate;
            }
            
            exposure.put(currency, exposure.getOrDefault(currency, 0.0) + amount);
        }
        
        // Calculate percentages
        Map<String, Double> exposurePercent = new HashMap<>();
        for (Map.Entry<String, Double> entry : exposure.entrySet()) {
            exposurePercent.put(entry.getKey(), entry.getValue() / totalValue);
        }
        
        return exposurePercent;
    }
    
    public void displayCurrencyRisk() {
        Map<String, Double> exposure = calculateCurrencyExposure();
        DecimalFormat pctFormat = new DecimalFormat("0.0%");
        
        System.out.println("\n===== CURRENCY EXPOSURE ANALYSIS =====");
        for (Map.Entry<String, Double> entry : exposure.entrySet()) {
            System.out.println(entry.getKey() + ": " + pctFormat.format(entry.getValue()));
        }
        
        // Recommendations based on exposure
        System.out.println("\nRECOMMENDATIONS:");
        for (Map.Entry<String, Double> entry : exposure.entrySet()) {
            String currency = entry.getKey();
            double exposurePercent = entry.getValue();
            
            if (!currency.equals("IDR") && exposurePercent > 0.2) {
                System.out.println("⚠️ High exposure to " + currency + ". Consider hedging to reduce currency risk.");
            }
        }
    }
    
    public Map<String, Double> simulateCurrencyShock(double shockPercent) {
        Map<String, Double> impact = new HashMap<>();
        double totalImpact = 0;
        
        for (Asset asset : portfolio.getAssets()) {
            if (!asset.getCurrency().equals("IDR")) {
                double assetValue = asset.getAmount();
                String pair = asset.getCurrency() + "/IDR";
                double rate = marketData.getCurrencyRate(pair);
                
                // Calculate impact of currency shock
                double valueInIDR = assetValue * rate;
                double impactAmount = valueInIDR * (shockPercent / 100);
                
                impact.put(asset.getName(), impactAmount);
                totalImpact += impactAmount;
            }
        }
        
        impact.put("TOTAL_IMPACT", totalImpact);
        return impact;
    }
}

// Stress testing for extreme market conditions
class StressTester {
    private TreasuryPortfolio portfolio;
    private MarketData marketData;
    private RiskAnalysis riskAnalysis;
    
    public StressTester(TreasuryPortfolio portfolio, MarketData marketData, RiskAnalysis riskAnalysis) {
        this.portfolio = portfolio;
        this.marketData = marketData;
        this.riskAnalysis = riskAnalysis;
    }
    
    public Map<String, Double> runLiquidityCrisisScenario() {
        // Simulate severe market liquidity crisis
        Map<String, Double> results = new HashMap<>();
        
        // Assume market liquidity drops to 20%
        double originalLiquidity = marketData.getLiquidityIndex();
        double crisisLiquidity = 0.2;
        
        // Calculate asset values under stress
        double totalValue = portfolio.getTotalValue();
        double liquidAssets = 0;
        
        for (Asset asset : portfolio.getAssets()) {
            if (asset.getType().equals("CASH")) {
                liquidAssets += asset.getAmount();
            } else {
                // Discount non-cash assets based on their liquidity rating
                // In a crisis, even relatively liquid assets take a hit
                double liquidityDiscount = 1.0 - (1.0 - asset.getLiquidityRating()) * (originalLiquidity / crisisLiquidity);
                liquidAssets += asset.getAmount() * Math.max(0.5, liquidityDiscount);
            }
        }
        
        // Calculate key metrics
        double liquidityRatio = liquidAssets / totalValue;
        
        // Get probability of cash shortfall from risk analysis
        Map<String, Double> liquidityRisk = riskAnalysis.runLiquidityRiskSimulation(30);
        double worstCase = liquidityRisk.get("WORST_CASE");
        double cashReserveRequired = totalValue * 0.2;
        
        results.put("LIQUIDITY_RATIO", liquidityRatio);
        results.put("SURVIVAL_DAYS", liquidityRatio * 100); // Rough estimate of days before cash exhaustion
        results.put("SHORTFALL_PROBABILITY", calculateProbabilityBelowTarget(worstCase, liquidityRisk.get("BEST_CASE"), cashReserveRequired));
        
        return results;
    }
    
    private double calculateProbabilityBelowTarget(double min, double max, double target) {
        if (target <= min) return 0.0;
        if (target >= max) return 1.0;
        
        // Simple linear approximation
        return (target - min) / (max - min);
    }
    
    public void runInterestRateShockScenario(double rateIncrease) {
        // Simulate sudden interest rate spike
        System.out.println("\n===== INTEREST RATE SHOCK SCENARIO =====");
        System.out.println("Scenario: Interest rates suddenly increase by " + rateIncrease + "%");
        
        // Impact on bond portfolio
        Map<String, Double> rateImpact = riskAnalysis.assessInterestRateRisk(rateIncrease);
        double totalImpact = rateImpact.get("TOTAL_IMPACT");
        
        // Display results
        DecimalFormat df = new DecimalFormat("#,##0.00");
        double portfolioValue = portfolio.getTotalValue();
        
        System.out.println("Portfolio value before shock: IDR " + df.format(portfolioValue));
        System.out.println("Estimated impact: IDR " + df.format(totalImpact));
        System.out.println("Portfolio value after shock: IDR " + df.format(portfolioValue + totalImpact));
        System.out.println("Percentage change: " + df.format((totalImpact / portfolioValue) * 100) + "%");
        
        // Resilience assessment
        double impactPercent = (totalImpact / portfolioValue) * 100;
        if (impactPercent < -10) {
            System.out.println("\nRESILIENCE: LOW - Portfolio is highly vulnerable to interest rate shocks");
            System.out.println("Recommended actions:");
            System.out.println("1. Reduce duration of fixed income holdings");
            System.out.println("2. Increase floating rate instruments");
            System.out.println("3. Consider interest rate hedging instruments");
        } else if (impactPercent < -5) {
            System.out.println("\nRESILIENCE: MEDIUM - Portfolio has moderate sensitivity to interest rate changes");
            System.out.println("Recommended actions:");
            System.out.println("1. Review duration strategy");
            System.out.println("2. Consider partial interest rate hedging");
        } else {
            System.out.println("\nRESILIENCE: HIGH - Portfolio is well-positioned for interest rate increases");
        }
    }
    
    public void displayStressTestResults() {
        System.out.println("\n===== COMPREHENSIVE STRESS TEST RESULTS =====");
        
        // Run liquidity crisis scenario
        Map<String, Double> liquidityCrisis = runLiquidityCrisisScenario();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        DecimalFormat pct = new DecimalFormat("0.0%");
        
        System.out.println("LIQUIDITY CRISIS SCENARIO:");
        System.out.println("Effective liquidity ratio: " + pct.format(liquidityCrisis.get("LIQUIDITY_RATIO")));
        System.out.println("Estimated survival period: " + df.format(liquidityCrisis.get("SURVIVAL_DAYS")) + " days");
        System.out.println("Probability of cash shortfall: " + pct.format(liquidityCrisis.get("SHORTFALL_PROBABILITY")));
        
        // Run interest rate shock scenarios
        System.out.println("\nINTEREST RATE SHOCK SCENARIOS:");
        for (double shock : new double[]{1.0, 2.0, 3.0}) {
            Map<String, Double> impact = riskAnalysis.assessInterestRateRisk(shock);
            double portfolioValue = portfolio.getTotalValue();
            double impactPercent = (impact.get("TOTAL_IMPACT") / portfolioValue) * 100;
            
            System.out.println(shock + "% rate increase: " + df.format(impactPercent) + "% portfolio impact");
        }
        
        // Run currency shock scenario
        CurrencyRiskManager crm = new CurrencyRiskManager(portfolio, marketData);
        Map<String, Double> currencyShock = crm.simulateCurrencyShock(10);
        double totalCurrencyImpact = currencyShock.get("TOTAL_IMPACT");
        double currencyImpactPercent = (totalCurrencyImpact / portfolio.getTotalValue()) * 100;
        
        System.out.println("\nCURRENCY SHOCK SCENARIO (10% depreciation):");
        System.out.println("Impact: " + df.format(currencyImpactPercent) + "% of portfolio value");
        
        // Overall resilience assessment
        System.out.println("\nOVERALL TREASURY RESILIENCE ASSESSMENT:");
        double overallScore = calculateResilienceScore(liquidityCrisis, currencyImpactPercent);
        
        if (overallScore >= 7.5) {
            System.out.println("STRONG (Score: " + df.format(overallScore) + "/10)");
            System.out.println("The treasury portfolio demonstrates robust resilience to various market stresses.");
        } else if (overallScore >= 5.0) {
            System.out.println("MODERATE (Score: " + df.format(overallScore) + "/10)");
            System.out.println("The treasury portfolio shows adequate resilience but has areas for improvement.");
        } else {
            System.out.println("VULNERABLE (Score: " + df.format(overallScore) + "/10)");
            System.out.println("The treasury portfolio shows significant vulnerabilities to market stresses.");
            System.out.println("Immediate action recommended to strengthen resilience.");
        }
    }
    
    private double calculateResilienceScore(Map<String, Double> liquidityCrisis, double currencyImpactPercent) {
        // Calculate score out of 10 based on various metrics
        double liquidityScore = Math.min(10, liquidityCrisis.get("LIQUIDITY_RATIO") * 10);
        double shortfallScore = Math.max(0, 10 - (liquidityCrisis.get("SHORTFALL_PROBABILITY") * 20));
        double currencyScore = Math.max(0, 10 - Math.abs(currencyImpactPercent));
        
        // Weighted average
        return (liquidityScore * 0.5) + (shortfallScore * 0.3) + (currencyScore * 0.2);
    }
}
