// MongoDB Initialization Script
db = db.getSiblingDB("ecommerce");

// Create collections
db.createCollection("users");
db.createCollection("categories");
db.createCollection("products");
db.createCollection("orders");

// Create indexes for better performance
db.users.createIndex({ email: 1 }, { unique: true });
db.categories.createIndex({ name: 1 }, { unique: true });
db.products.createIndex({ name: "text", description: "text" });
db.products.createIndex({ "category.id": 1 });
db.products.createIndex({ isFeatured: 1 });
db.orders.createIndex({ "user.id": 1 });
db.orders.createIndex({ status: 1 });
db.orders.createIndex({ dateOrdered: -1 });

// Insert sample categories
db.categories.insertMany([
  {
    _id: ObjectId(),
    name: "Electronics",
    icon: "fa-laptop",
    color: "#3498db",
  },
  {
    _id: ObjectId(),
    name: "Clothing",
    icon: "fa-tshirt",
    color: "#e74c3c",
  },
  {
    _id: ObjectId(),
    name: "Books",
    icon: "fa-book",
    color: "#f39c12",
  },
  {
    _id: ObjectId(),
    name: "Home & Garden",
    icon: "fa-home",
    color: "#27ae60",
  },
]);

print("Database initialized with sample data");
