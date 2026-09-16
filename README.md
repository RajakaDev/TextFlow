[README.md](https://github.com/user-attachments/files/32279185/README.md)
# Supplier & Purchase Management Module

C# WinForms desktop app covering the **Supplier & Purchase Management** part of the POS project (owns: `suppliers`, `purchases`, `purchase_items`, `purchase returns`; reads `products` and `users` from the other modules).

## What's included

- **Models/** — `Supplier`, `Purchase`, `PurchaseItem`, `PurchaseReturn`
- **DAO/** — `SupplierDAO` (full CRUD + search), `PurchaseDAO` (start/edit/delete draft purchases, item CRUD, confirm purchase → stock+, purchase return → stock−, history & totals), `ProductLookupDAO` (read-only product lookup + the one agreed stock-adjustment exception)
- **Forms/** — `MainForm` (menu), `SupplierForm` (CRUD UI), `PurchaseForm` (create a draft purchase, scan/lookup products, add/remove items, confirm), `PurchaseReturnForm`, `PurchaseHistoryForm` (filterable history + report totals)
- **Data/DatabaseConnection.cs** — standalone MySQL connection helper. **This is a placeholder** — once your team's shared `DatabaseConnection` class exists, delete this file and point the DAOs at that one instead (keep the same `GetConnection()` signature so nothing else breaks).
- **Database/schema.sql** — creates the database, this module's 4 tables, and small stub `products`/`users` tables so the app runs standalone before merging with Anurasiri's and Fernando's modules.

## Setup (Visual Studio)

1. **Install MySQL** locally (or point at your team's shared server) and run `Database/schema.sql` (e.g. via MySQL Workbench or `mysql -u root -p < schema.sql`).
2. Open `SupplierPurchaseManagement.sln` in Visual Studio 2022 (Community edition is fine).
3. Visual Studio will restore the `MySql.Data` NuGet package automatically on first build (or right-click the project → *Manage NuGet Packages* → restore).
4. Open `Data/DatabaseConnection.cs` and edit the `Server`, `Database`, `User`, `Password` constants to match your MySQL setup.
5. Press **F5** to run. `MainForm` will show a green/red status label depending on whether it connected.

## Requirements

- Visual Studio 2022+
- .NET 6.0 (the project targets `net6.0-windows`; if your machine only has .NET 8, right-click project → Properties → change Target Framework, or install the .NET 6 SDK)
- MySQL Server 5.7+ / 8.0+

## Merging with the rest of the team

- Replace `Data/DatabaseConnection.cs` with the team's single shared class once it exists.
- Drop the stub `products` and `users` tables from `schema.sql` once Anurasiri's and Fernando's real tables exist with the same column names (`products.product_id/name/barcode/current_stock`, `users.user_id`).
- `CurrentUserId` in `PurchaseForm.cs` is hardcoded to `1` — swap it for whatever the real logged-in user lookup is (e.g. `CurrentUser.UserId`) once Authentication is wired in.
- This module never writes to `products` except the one agreed stock +/− adjustment on confirm/return — everything else about products stays in Anurasiri's module, per the team's ownership table.

## Pushing to GitHub

```bash
cd SupplierPurchaseManagement
git init
git add .
git commit -m "Supplier & Purchase Management module"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```

A starter `.gitignore` for Visual Studio/C# is included so `bin/`, `obj/`, and user-specific files aren't committed.
