package G207DBAPP;
import java.util.Scanner;
import java.util.List;
import java.io.*;

public class product_management_menu {

    public product_management_menu() {
    }

    public int menu() {
        int menuselection = -1;
        Scanner console = new Scanner(System.in);
        product_management p = new product_management();

        while (true) {
            // Display the Menu
            System.out.println();
            System.out.println("=======================================================");
            System.out.println("                   Product Management Menu             ");
            System.out.println("=======================================================");
            System.out.println("|  [1] Create a New Product                            |");
            System.out.println("|  [2] Update a Product Record                         |");
            System.out.println("|  [3] Delete a Product Record                         |");
            System.out.println("|  [4] Discontinue a Product                           |");
            System.out.println("|  [5] View a Product Record                           |");
            System.out.println("|  [6] Make a Product Current                          |"); // New option
            System.out.println("|  [0] Exit Product Management                         |");
            System.out.println("=======================================================");

            System.out.print("Enter Selected Function: ");
            String input = console.nextLine();

            try {
                menuselection = Integer.parseInt(input);
                if (menuselection < 0 || menuselection > 6) { // Adjust the range
                    System.out.println("Invalid selection. Please enter a number between 0 and 6.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            if (menuselection == 0) {
                break;  // Exit the menu
            }

            switch (menuselection) {
                case 1:
                    createNewProduct(console, p);
                    break;
                case 2:
                    updateProduct(console, p);
                    break;
                case 3:
                    deleteProduct(console, p);
                    break;
                case 4:
                    discontinueProduct(console, p);
                    break;
                case 5:
                    viewProduct(console, p);
                    break;
                case 6:
                    makeProductCurrent(console, p); // New case
                    break;
            }
        }
        return menuselection;
    }

    // Other methods...

    private void makeProductCurrent(Scanner console, product_management p) {
        System.out.println("Enter product information:");

        System.out.print("Product Code: ");
        p.productCode = console.nextLine();

        if (p.get_product() == 0) {
            System.out.println("This Product does not exist");
            return;
        }

        displayProductInfo(p);

        if (!p.isProductDiscontinued()) {
            System.out.println("Product is not discontinued");
            return;
        }
        p.make_product_current();
    }

    private void displayProductInfo(product_management p) {
        System.out.println("Current Product information");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Product Code        : " + p.productCode);
        System.out.println("Product Name        : " + p.productName);
        System.out.println("Product Line        : " + p.productLine);
        System.out.println("Product Scale       : " + p.productScale);
        System.out.println("Product Description : " + p.productDescription);
        System.out.println("Product Vendor      : " + p.productVendor);
        System.out.println("Initial quantity    : " + p.quantityInStock);
        System.out.println("Buy Price           : " + String.format("%.2f", p.buyPrice));
        System.out.println("MSRP                : " + String.format("%.2f", p.MSRP));
        System.out.println("-------------------------------------------------------------------");
    }

    // Existing methods for creating, updating, deleting, discontinuing, and viewing products
    private void createNewProduct(Scanner console, product_management p) {
        System.out.println("Enter product information:");
        System.out.println("---------------------------------------------");

        System.out.print("Product Code: ");
        p.productCode = console.nextLine();

        if (p.get_product() > 0) {
            System.out.println("This Product Already Exists");
            return;
        }

        System.out.print("Product Name: ");
        p.productName = console.nextLine();

        while (true) {
            System.out.println("Select a Product Line:");
            List<String> validProductLines = p.getValidProductLines();
            for (int i = 0; i < validProductLines.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + validProductLines.get(i));
            }
            System.out.print("Enter selection (1-" + validProductLines.size() + "): ");
            try {
                int selection = Integer.parseInt(console.nextLine());
                if (selection < 1 || selection > validProductLines.size()) {
                    throw new NumberFormatException();
                }
                p.productLine = validProductLines.get(selection - 1);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection. Please enter a number between 1 and " + validProductLines.size() + ".");
            }
        }

        while (true) {
            System.out.print("Product Scale [1:n]: ");
            p.productScale = console.nextLine();
            if (!p.isProductScaleValid(p.productScale)) {
                System.out.println("Invalid Product Scale. Format should be 1:n");
            } else {
                break;
            }
        }

        System.out.print("Product Description: ");
        p.productDescription = console.nextLine();

        System.out.print("Product Vendor: ");
        p.productVendor = console.nextLine();

        while (true) {
            try {
                System.out.print("Initial quantity: ");
                p.quantityInStock = Integer.parseInt(console.nextLine());
                if (p.quantityInStock < 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for quantity. Please enter a non-negative integer.");
            }
        }

        while (true) {
            try {
                System.out.print("Buy Price: ");
                p.buyPrice = Float.parseFloat(console.nextLine());
                if (p.buyPrice < 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for Buy Price. Please enter a valid number.");
            }
        }

        while (true) {
            try {
                System.out.print("MSRP: ");
                p.MSRP = Float.parseFloat(console.nextLine());
                if (p.MSRP < 0) {
                    throw new NumberFormatException();
                }
                if (p.buyPrice > p.MSRP) {
                    System.out.println("Buy Price cannot be more than MSRP. Please enter a valid price.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for MSRP. Please enter a valid number.");
            }
        }

        p.add_product();
    }

    private void updateProduct(Scanner console, product_management p) {
        System.out.println("Enter product information:");

        System.out.print("Product Code: ");
        p.productCode = console.nextLine();

        if (p.get_product() == 0) {
            System.out.println("This Product does not exist");
            return;
        } else if (p.isProductDiscontinued()) {
        	displayProductInfo(p);
            System.out.println("Discontinued Products cannot be updated");
            return;
        } else {
            displayProductInfo(p);

            System.out.println("Enter updated product information:");

            System.out.print("Product Name: ");
            p.productName = console.nextLine();

            while (true) {
                System.out.println("Select a Product Line:");
                List<String> validProductLines = p.getValidProductLines();
                for (int i = 0; i < validProductLines.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + validProductLines.get(i));
                }
                System.out.print("Enter selection (1-" + validProductLines.size() + "): ");
                try {
                    int selection = Integer.parseInt(console.nextLine());
                    if (selection < 1 || selection > validProductLines.size()) {
                        throw new NumberFormatException();
                    }
                    p.productLine = validProductLines.get(selection - 1);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid selection. Please enter a number between 1 and " + validProductLines.size() + ".");
                }
            }

            while (true) {
                System.out.print("Product Scale [1:n]: ");
                p.productScale = console.nextLine();
                if (!p.isProductScaleValid(p.productScale)) {
                    System.out.println("Invalid Product Scale. Format should be 1:n");
                } else {
                    break;
                }
            }

            System.out.print("Product Description: ");
            p.productDescription = console.nextLine();

            System.out.print("Product Vendor: ");
            p.productVendor = console.nextLine();

            while (true) {
                try {
                    System.out.print("Initial quantity: ");
                    p.quantityInStock = Integer.parseInt(console.nextLine());
                    if (p.quantityInStock < 0) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for quantity. Please enter a non-negative integer.");
                }
            }

            while (true) {
                try {
                    System.out.print("Buy Price: ");
                    p.buyPrice = Float.parseFloat(console.nextLine());
                    if (p.buyPrice < 0) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for Buy Price. Please enter a valid number.");
                }
            }

            while (true) {
                try {
                    System.out.print("MSRP: ");
                    p.MSRP = Float.parseFloat(console.nextLine());
                    if (p.MSRP < 0) {
                        throw new NumberFormatException();
                    }
                    if (p.buyPrice > p.MSRP) {
                        System.out.println("Buy Price cannot be more than MSRP. Please enter a valid price.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for MSRP. Please enter a valid number.");
                }
            }

            p.update_product();
        }
    }

    private void deleteProduct(Scanner console, product_management p) {
        System.out.println("Enter product information:");

        System.out.print("Product Code: ");
        p.productCode = console.nextLine();

        if (p.get_product() == 0) {
            System.out.println("This Product does not exist");
            return;
        }

        displayProductInfo(p);

        if (p.isProductDiscontinued()) {
            System.out.println("Discontinued Products cannot be deleted");
            return;
        }
        p.delete_product();
    }

    private void discontinueProduct(Scanner console, product_management p) {
        System.out.println("Enter product information:");

        System.out.print("Product Code: ");
        p.productCode = console.nextLine();

        if (p.get_product() == 0) {
            System.out.println("This Product does not exist");
            return;
        }

        displayProductInfo(p);

        if (p.isProductDiscontinued()) {
            System.out.println("Product is already discontinued");
            return;
        }
        p.discontinue_product();
    }

    private void viewProduct(Scanner console, product_management p) {
        System.out.println("Enter product information:");

        System.out.print("Product Code: ");
        p.productCode = console.nextLine();

        if (p.get_product() == 0) {
            System.out.println("This Product does not exist");
        } else {
            displayProductInfo(p);
            System.out.print("Enter the year to view orders: ");
            int year = Integer.parseInt(console.nextLine());
            p.get_product_orders(year);
        }
    }
}
