# Source Code
The complete Java source code for this project is hosted on my GitHub:
[MenuManager GitHub Repository](https://github.com/liamlin-ship-it/MenuManager)

# Project Overview
**About This Project**
I built this MenuManager using Java to challenge myself with more complex data handling. My goal for my first year is to consistently turn programming concepts into working software, rather than just solving isolated exercises. This tool provides a clean, text-based interface to easily add, manage, and navigate menu items

**Why I Built This**
I've always been curious about how backend systems organize and manage data. Instead of just reading about Object-Oriented Programming, I decided to apply those concepts to build a structured management tool. Creating this project was a great way to solidify my Java fundamentals while designing something that mimics real-world system logic.

**What It Can Do**
* `Dynamic Ordering`: Selecting a basic item adds it directly to the cart. If a beverage is selected, the system automatically prompts for "sugar" and "ice" levels for customization.
* `Cart Management`: Users can cancel items at any time. The system displays the current order list, allowing users to remove an item simply by entering its corresponding sequence number.
* `Smart Checkout`: A loop automatically calculates the total amount, reminds users of the $5 minimum order requirement, applies a 10% discount business logic for orders over $20, and gracefully handles payment and change.

**What I Practiced**
* Java Basics:

  Used `ArrayList` to store and manage a dynamic, variable number of order items.

  Implemented `try-catch` blocks to handle input exceptions, preventing the system from crashing when users enter invalid text.

* Object-Oriented Programming

  Inheritance: Created a base `MenuItem` parent class and had the `Beverage` class inherit from it to expand customization attributes.

  Polymorphism: Overrode the `getDetails()` method, allowing the main program to use a single, simple loop to correctly format and print both regular meals and customized beverages.

  Object References: Learned to use `instanceof` to check object types and instantiate brand-new beverage objects (`new Beverage(...)`). This prevents a customer's custom choices from modifying and polluting the global menu's memory data.

# Phase 1: Defining the Base Menu Class
**Purpose:** Encapsulate the common attributes shared by all menu items (such as ID, name, and price) to establish a solid, reusable foundation.

# Phase 2: Inheritance and Customization
**Purpose:** Utilize inheritance (`Beverage extends MenuItem`) to handle drink items that require extra customization (sugar and ice levels). This approach eliminates code duplication and enhances the system's scalability through method overriding.

# Phase 3: Building the Order Management System
**Purpose:** Use an `ArrayList` to centrally manage the user's shopping cart. The logic for adding items, removing items, and calculating the total checkout price is encapsulated entirely within this `Order` class.

# Phase 4: Main Program Implementation
**Purpose:** Integrate all object classes, handle user interactions and execute the actual business logic.

# Phase 5: Challenges & Bug Resolution
**1. Leveraging Inheritance and Polymorphism**

**The Challenge:** The menu consists of both standard meals `MenuItem` and drinks `Beverage`. Drinks possess extra attributes (ice and sugar levels). Initially, I was confused about how to store two different types of objects in a single cart `ArrayList<MenuItem>` and how to correctly print their details without writing clunky, repetitive if-else type checks.

**The Solution:** I solved this by applying Polymorphism. I had the `Beverage` class inherit from `MenuItem` and then overrode the `getDetails()` method. By calling `super.getDetails()` first, I could reuse the parent's formatting and simply append the customization string. Now, a single loop can seamlessly print the correct format for any type of item in the cart!

```java
@Override
    public String getDetails(){
        // Retrieve the pre-formatted ID, name, and price from the parent class,
        // then append the sugar and ice level strings.
        return super.getDetails() + String.format("%s, %s", sugarLevel, iceLevel);
    }
```

**2. System Crashes from Unpredictable User Inputs**

**The Challenge:** During the "Cancel Item" or "Checkout" processes, the system expected numeric inputs (like an item index). However, if a user accidentally typed a letter or hit the spacebar, the console immediately threw a `NumberFormatException` and the entire program crashed, wiping out all the cart data.

```java
int index = Integer.parseInt(scanner.nextLine());
currentOrder.removeItem(index - 1);
```

**System Crash on Invalid Input**
![螢幕擷取畫面 2026-07-16 144909](https://hackmd.io/_uploads/rkilkbINMe.png)

**The Solution:** To improve system robustness, I wrapped all numeric input sections in a `try-catch` block. When an exception is caught, the system displays an error message and uses a `while` loop to guide the user back to the input prompt, ensuring the application remains stable.

```java
try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index == 0) return;
            // Array index starts at 0, so subtract 1
            currentOrder.removeItem(index - 1);
        } catch (NumberFormatException e) {
            // Catch exceptions where the user inputs a non-numeric string
            System.out.println("Please enter a valid number!");
        }
```

![螢幕擷取畫面 2026-07-16 145438](https://hackmd.io/_uploads/ryLcxZIVGg.png)

**3. Unintended Memory Mutation**

**The Challenge:** While developing the beverage customization feature, I encountered a critical bug. If a customer added an "Americano" to their cart and set it to "No Ice", the default Americano on the main menu was also overridden to "No Ice". The global menu data was essentially corrupted for the next customer.

```java
        if (selectedItem instanceof Beverage) {
            System.out.print("Enter sugar level (Regular/Half/Low/None): ");
            String sugar = scanner.nextLine();
            System.out.print("Enter ice level (Regular/Less/Low/None): ");
            String ice = scanner.nextLine();

            Beverage wrongDrink = (Beverage) selectedItem; 
            wrongDrink.setCustomization(sugar, ice);
            currentOrder.addItem(wrongDrink);
        } else {
            currentOrder.addItem(selectedItem);
        }
```

**The Menu Has Been Changed**
![螢幕擷取畫面 2026-07-16 151145](https://hackmd.io/_uploads/H1XrEWIVMg.png)

**The Solution:** Through debugging, I learned how Java handles Object References. By adding the menu item directly to the cart, both the cart and the menu were pointing to the exact same memory address. Changing one changed the other.
To resolve this, I used the new keyword to instantiate a brand-new object for the customized drink. This created an independent memory space, ensuring the original menu remains untouched.

```java
// If it's a beverage, instantiate a new Beverage object for customization to avoid modifying the original menu
        // Check if selectedItem is an instance of Beverage; if not, go to the else block
        if (selectedItem instanceof Beverage b) {
            System.out.print("Enter sugar level (Regular/Half/Low/None): ");
            String sugar = scanner.nextLine();
            System.out.print("Enter ice level (Regular/Less/Low/None): ");
            String ice = scanner.nextLine();

            Beverage customDrink = new Beverage(b.getId(), b.getName(), b.getPrice());
            customDrink.setCustomization(sugar, ice);
            currentOrder.addItem(customDrink);
        } else {
            // Add regular items directly to the cart
            currentOrder.addItem(selectedItem);
        }
```

![螢幕擷取畫面 2026-07-16 151451](https://hackmd.io/_uploads/SJhSH-IEzl.png)

# Phase 6: Final System Test
After resolving all the bugs and refining the console layout, I conducted a comprehensive system test. I simulated a complete order consisting of 5 mixed items.

The checkout output below successfully verifies that the beverage customization string concatenation, the 10% discount logic for meeting the price threshold, and the payment validation mechanisms are all functioning perfectly.

```
========== Receipt ==========
F02   Original Croissant   3.25    
D01   Americano            4.75    None, None
F03   Tiramisu             6.00    
F01   Basque Cheesecake    5.50    
D02   Cafe Latte           5.00    Low, Low

Original Total: $24.5
Orders over $20 receive a 10% discount!
Discounted Total: $22.05

Enter payment amount: $20
Insufficient funds! You are short by $2.05. Please re-enter.
Enter payment amount: $25

Checkout successful!
Amount Received: $25.0
Change: $2.9
Number of Items: 5 items
Thank you for visiting, see you next time!
```

# Learning Reflection
**What I Learned:** This project made me realize that "making the code work" and "building a stable system" are two totally different things! Through a lot of trial and error and getting help from AI to debug, I learned how important it is to plan out the basic structure before I actually start typing code. It really saved me from getting completely lost in my own logic later on.

**Next Steps:** Even though the checkout and change calculation features work well now, all the data is just stored in the memory. As soon as I close the console, all the sales records are gone. As I continue to learn, my next goal is to figure out how to use File I/O to save the menu and transaction history permanently.
