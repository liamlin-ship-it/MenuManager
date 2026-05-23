import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<MenuItem> menu = new ArrayList<>();
        Order currentOrder = new Order();

        // 初始化寫死的菜單
        initializeMenu(menu);

        boolean running = true;
        while (running) {
            System.out.println("\n========== Java Cafe ==========");
            System.out.println("本店每人低消 $150 元!");
            System.out.println("1. 查看菜單");
            System.out.println("2. 開始點餐");
            System.out.println("3. 取消餐點");
            System.out.println("4. 結帳付款");
            System.out.println("0. 離開系統");
            System.out.print("請輸入選項: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayMenu(menu);
                    break;

                case "2":
                    orderItem(scanner, menu, currentOrder);
                    break;

                case "3":
                    cancelItem(scanner, currentOrder);
                    break;

                case "4":
                    boolean isCheckedOut = checkout(scanner, currentOrder);
                    // 如果結帳成功，清空購物車，並可以選擇是否繼續服務下一組客人
                    if (isCheckedOut) {
                        currentOrder.clear();
                        System.out.println("\n準備迎接下一組顧客");
                    }
                    break;

                case "0":
                    System.out.println("感謝您的使用，系統已關閉！");
                    running = false;
                    break;

                default:
                    // 防止輸入 0~4 以外的數字
                    System.out.println("輸入錯誤，請重新輸入有效的數字。");
            }
        }
        scanner.close();
    }

    private static void initializeMenu(ArrayList<MenuItem> menu) {
        menu.add(new MenuItem("F01", "巴斯克乳酪蛋糕", 120));
        menu.add(new MenuItem("F02", "原味可頌", 80));
        menu.add(new MenuItem("F03", "提拉米蘇", 130));

        // 建立飲料時，使用預設建構子
        menu.add(new Beverage("D01", "美式咖啡", 140));
        menu.add(new Beverage("D02", "拿鐵咖啡", 150));
        menu.add(new Beverage("D03", "伯爵紅茶", 120));
    }

    private static void displayMenu(ArrayList<MenuItem> menu) {
        System.out.println("\n=== 本日菜單 ===");
        System.out.println();

        // 表格標題列
        System.out.println("編號  品項名稱        價格     客製化選項");
        // 呼叫 getDetails() 並輸出菜單內容
        for (MenuItem item : menu) {
            System.out.println(item.getDetails());
        }
    }

    private static void orderItem(Scanner scanner, ArrayList<MenuItem> menu, Order currentOrder) {
        displayMenu(menu);
        System.out.print("請輸入餐點編號 (或輸入 s 返回): ");
        String id = scanner.nextLine().toUpperCase(); // 轉大寫防呆

        if (id.equals("S")) return;

        MenuItem selectedItem = null;
        for (MenuItem item : menu) {
            if (item.getId().equals(id)) {
                selectedItem = item;
                break;
            }
        }

        if (selectedItem == null) {
            System.out.println("找不到該餐點編號！");
            return;
        }

        // 如果是飲料，必須 new 一個新的 Beverage 物件來客製化，避免改到菜單原檔
        if (selectedItem instanceof Beverage) {
            System.out.print("請輸入甜度 (正常糖/半糖/微糖/無糖): ");
            String sugar = scanner.nextLine();
            System.out.print("請輸入冰塊 (正常冰/少冰/微冰/去冰): ");
            String ice = scanner.nextLine();

            Beverage customDrink = new Beverage(selectedItem.getId(), selectedItem.getName(), selectedItem.getPrice());
            customDrink.setCustomization(sugar, ice);
            currentOrder.addItem(customDrink);
        } else {
            // 一般餐點直接加入購物車
            currentOrder.addItem(selectedItem);
        }
    }

    private static void cancelItem(Scanner scanner, Order currentOrder) {
        if (currentOrder.getItems().isEmpty()) {
            System.out.println("購物車目前是空的！");
            return;
        }

        System.out.println("\n=== 目前購物車 ===");
        for (int i = 0; i < currentOrder.getItems().size(); i++) {
            System.out.println((i + 1) + ". " + currentOrder.getItems().get(i).getDetails());
        }
        System.out.print("請輸入要取消的清單序號 (或輸入 0 返回): ");

        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index == 0) return;
            // 陣列索引從 0 開始，所以要減 1
            currentOrder.removeItem(index - 1);
        } catch (NumberFormatException e) {
            // 捕捉使用者輸入非數字字串的例外情況
            System.out.println("請輸入有效的數字！");
        }
    }

    private static boolean checkout(Scanner scanner, Order currentOrder) {
        if (currentOrder.getItems().isEmpty()) {
            System.out.println("您尚未點選任何餐點！");
            return false;
        }

        int total = currentOrder.calculateTotal();
        System.out.println("\n========== 消費明細 ==========");
        for (MenuItem item : currentOrder.getItems()) {
            System.out.println(item.getDetails());
        }
        System.out.println();
        System.out.println("原始總計: $" + total);

        // 低消限制提醒
        if (total < 150) {
            System.out.println("尚未達到每人低消 $150 元！");
        }

        // 滿 500 打 9 折
        int finalTotal = total;
        if (total >= 500) {
            finalTotal = (int) (total * 0.9);
            System.out.println("消費滿 $500，享 9 折優惠！");
            System.out.println("折扣後總計: $" + finalTotal);
        } else {
            System.out.println("應付總計: $" + finalTotal);
        }
        System.out.println();

        // 付款與找零
        while (true) {
            System.out.print("請輸入付款金額: $");
            try {
                int payment = Integer.parseInt(scanner.nextLine());
                if (payment < finalTotal) {
                    System.out.println("金額不足！還差 $" + (finalTotal - payment) + "，請重新輸入。");
                } else {
                    int change = payment - finalTotal;
                    System.out.println("\n結帳成功！");
                    System.out.println("實收金額: $" + payment);
                    System.out.println("找零金額: $" + change);
                    System.out.println("品項數: " + currentOrder.getItems().size() + " 項");
                    System.out.println("謝謝光臨，歡迎下次再來！\n");
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("請輸入有效的數字金額！");
            }
        }
    }
}
