package com.delichops.common;


public class BannerPrinter {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";

    public static void printBanner(String serviceName) {
        System.out.println("\n");
        System.out.println(GREEN + "╔══════════════════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██████╗ ███████╗██╗     ██╗ ██████╗██╗  ██╗ ██████╗ ██████╗ ███████╗" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██╔══██╗██╔════╝██║     ██║██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔════╝" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██║  ██║█████╗  ██║     ██║██║     ███████║██║   ██║██████╔╝███████╗" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██║  ██║██╔══╝  ██║     ██║██║     ██╔══██║██║   ██║██╔═══╝ ╚════██║" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██████╔╝███████╗███████╗██║╚██████╗██║  ██║╚██████╔╝██║     ███████║" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "╚═════╝ ╚══════╝╚════╝╚═╝ ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚══════╝" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║              " + YELLOW + "🍕  F O O D   D E L I V E R Y   P L A T F O R M  🍔" + GREEN + "             ║" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║                    " + CYAN + serviceName + GREEN + "                                           ║" + RESET);
        System.out.println(GREEN + "╚══════════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println("\n");
    }

    public static void printAuthServiceBanner() {
        System.out.println("\n");
        System.out.println(GREEN + "╔══════════════════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██████╗ ███████╗██╗     ██╗ ██████╗██╗  ██╗ ██████╗ ██████╗ ███████╗" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██╔══██╗██╔════╝██║     ██║██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔════╝" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██║  ██║█████╗  ██║     ██║██║     ███████║██║   ██║██████╔╝███████╗" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██║  ██║██╔══╝  ██║     ██║██║     ██╔══██║██║   ██║██╔═══╝ ╚════██║" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██████╔╝███████╗███████╗██║╚██████╗██║  ██║╚██████╔╝██║     ███████║" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "╚═════╝ ╚══════╝╚══════╝╚═╝ ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚══════╝" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║              " + YELLOW + "🍕  F O O D   D E L I V E R Y   P L A T F O R M  🍔" + GREEN + "             ║" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║                    " + CYAN + "🔐  A U T H   S E R V I C E  🔐" + GREEN + "                          ║" + RESET);
        System.out.println(GREEN + "╚══════════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println("\n");
    }

    public static void printGatewayBanner() {
        System.out.println("\n");
        System.out.println(GREEN + "╔══════════════════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██████╗ ███████╗██╗     ██╗ ██████╗██╗  ██╗ ██████╗ ██████╗ ███████╗" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██╔══██╗██╔════╝██║     ██║██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔════╝" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██║  ██║█████╗  ██║     ██║██║     ███████║██║   ██║██████╔╝███████╗" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██║  ██║██╔══╝  ██║     ██║██║     ██╔══██║██║   ██║██╔═══╝ ╚════██║" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██████╔╝███████╗███████╗██║╚██████╗██║  ██║╚██████╔╝██║     ███████║" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "╚═════╝ ╚══════╝╚══════╝╚═╝ ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚══════╝" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║              " + YELLOW + "🍕  F O O D   D E L I V E R Y   P L A T F O R M  🍔" + GREEN + "             ║" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║                    " + CYAN + "🌐  A P I   G A T E W A Y  🌐" + GREEN + "                            ║" + RESET);
        System.out.println(GREEN + "╚══════════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println("\n");
    }

    public static void printEurekaBanner() {
        System.out.println("\n");
        System.out.println(GREEN + "╔══════════════════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██████╗ ███████╗██╗     ██╗ ██████╗██╗  ██╗ ██████╗ ██████╗ ███████╗" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██╔══██╗██╔════╝██║     ██║██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔════╝" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██║  ██║█████╗  ██║     ██║██║     ███████║██║   ██║██████╔╝███████╗" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██║  ██║██╔══╝  ██║     ██║██║     ██╔══██║██║   ██║██╔═══╝ ╚════██║" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "██████╔╝███████╗███████╗██║╚██████╗██║  ██║╚██████╔╝██║     ███████║" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║   " + CYAN + "╚═════╝ ╚══════╝╚══════╝╚═╝ ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚══════╝" + GREEN + "     ║" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║              " + YELLOW + "🍕  F O O D   D E L I V E R Y   P L A T F O R M  🍔" + GREEN + "             ║" + RESET);
        System.out.println(GREEN + "║                                                                              ║" + RESET);
        System.out.println(GREEN + "║                 " + CYAN + "📡  E U R E K A   S E R V E R  📡" + GREEN + "                           ║" + RESET);
        System.out.println(GREEN + "╚══════════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println("\n");
    }

    public static void main(String[] args) {
        // Test the banners
        printAuthServiceBanner();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        printGatewayBanner();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        printEurekaBanner();
    }
}
