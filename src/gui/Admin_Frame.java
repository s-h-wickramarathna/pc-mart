
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import javax.swing.JOptionPane;
import model.MYSQL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import model.Customer;
import model.Employee;
import model.Product;
import model.Supplier;

/**
 *
 * @author sanch
 */
public class Admin_Frame extends javax.swing.JFrame {

    //Employee
    HashMap<String, Employee> empMap = new HashMap<>();
    Vector<String> empVector = new Vector<>();
    //Employee

    //Customer
    HashMap<String, Customer> customerMap = new HashMap<>();
    Vector<String> customerVector = new Vector<>();
    //Customer

    //Brand
    HashMap<String, Integer> brandMap = new HashMap<>();
    Vector<String> brandVector = new Vector<>();
    //Brand

    //Warrenty
    HashMap<String, Integer> warrentyMap = new HashMap<>();
    Vector<String> warrentyVector = new Vector<>();
    //Warrenty

    //Product
    HashMap<String, Product> productMap = new HashMap<>();
    Vector<String> productVector = new Vector<>();
    //Product

    //Companies
    HashMap<String, String> companyMap = new HashMap<>();
    Vector<String> companyVector = new Vector<>();
    //Companies

    //Suppliers
    HashMap<String, Supplier> supplierMap = new HashMap<>();
    Vector<String> supplierVector = new Vector<>();
    //Suppliers

    // Direct SQL Table Load
    //GRN Supplier
    HashMap<String, String> stockWarrenttyID = new HashMap<>();

    public static String pid = "";
    public static String pname = "";
    public static String pbname = "";
    public static String GrnsuppilerEmail = "";
    public static boolean newStock = true;
    public static String GrnStockID = "";
    public static String GrnWarrenty = "";
    public static String GrnBrand = "";
    public static String GrnName = "";
    public static String GrnBuyPrice = "";
    public static String GrnSellPrice = "";
    public static String GrnQty = "";
    public static String GrnCompanyId = "";

    boolean firstRowIsAdded = false;
    int totalProducts = 0;
    int totalQty = 0;
    int totalPrice = 0;

    boolean invoiceFirstRowIsAdded = false;
    int invoiceTotalProducts = 0;
    int invoiceTotalQty = 0;
    int invoiceTotalPrice = 0;

    public static String InvoiceCustomerEmail;
    public static String InvoiceStockID;
    public static String InvoiceProductName;
    public static String InvoiceBrandName;
    public static String InvoiceUnitePrice;

    public Admin_Frame() {
        initComponents();

        loadEmployees();
        loadEmployeeTable();

        loadCustomers();
        loadCustomerTable();

        loadBrand();
        loadBrandTable();
        loadBrandComboBox();
        loadWarrentyComboBox();
        loadProduct();
        loadProductTable();

        loadCompanies();
        loadCopaniesTable();
        loadSuppliers();
        loadSuppliersTable();

        loadWarrentyCombobox();
        loadGRNStockTable();
        setUniqueStockID();

        loadInvoiceDate();

    }

    private void loadEmployees() {

        empMap.clear();
        empVector.clear();

        try {
            ResultSet resultset = MYSQL.Search("SELECT * FROM `employee` ORDER BY `firstName` ASC");

            while (resultset.next()) {
                empVector.add(resultset.getString("email"));
                Employee employee = new Employee();
                employee.setEmail(resultset.getString("email"));
                employee.setFirstName(resultset.getString("firstName"));
                employee.setLastName(resultset.getString("lastName"));
                employee.setPassword(resultset.getString("password"));
                employee.setMobile(resultset.getString("mobile"));
                employee.setEmployeeTypeID(resultset.getInt("employee_type_id"));
                employee.setGenderID(resultset.getInt("gender_id"));
                employee.setDateOfBirth(resultset.getString("dob"));
                employee.setJoinDate(resultset.getString("join_date"));
                empMap.put(resultset.getString("email"), employee);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void Alert(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String validateEmployeeFields() {
        if (jComboBox2.getSelectedIndex() == 0) {
            return "Select employee Type ....";

        } else if (jTextField7.getText().isEmpty()) {
            return "Enter Employee First Name ....";

        } else if (jTextField1.getText().isEmpty()) {
            return "Enter Employee Last Name ....";

        } else if (jTextField5.getText().isEmpty()) {
            return "Enter Employee Mobile Number ....";

        } else if (!jTextField5.getText().matches("^07[1245678]{1}[0-9]{7}$")) {
            return "Invalid Mobile Number ....";

        } else if (jComboBox1.getSelectedIndex() == 0) {
            return "Select Employee Gender ....";

        } else if (jTextField3.getText().isEmpty()) {
            return "Enter Employee Email Address ....";

        } else if (!jTextField3.getText().matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            return "Invalid Email Address ....";

        } else if (String.valueOf(jPasswordField1.getPassword()).isEmpty()) {
            return "Enter Employee Password ....";

        } else {
            return "Success";

        }
    }

    private void resetEmployeeFields() {

        jComboBox2.setSelectedIndex(0);
        jTextField7.setText("");
        jTextField1.setText("");
        jTextField5.setText("");
        jComboBox1.setSelectedIndex(0);
        jTextField3.setText("");
        jPasswordField1.setText("");
        jTextField15.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        jComboBox5.setEnabled(true);
        jTextField2.setEnabled(true);
        jButton5.setEnabled(true);
        jButton4.setEnabled(false);
        jButton1.setVisible(false);
        jTextField16.setVisible(false);
        jLabel24.setVisible(false);
        jComboBox5.setSelectedIndex(0);
        jTextField2.setText("Search By Email Or Mobile No ....");
        jTextField2.setForeground(Color.GRAY);
        jTable1.setEnabled(true);

        jComboBox1.setEnabled(true);
        jTextField3.setEnabled(true);
        jTextField15.setEnabled(true);
        jTextField7.grabFocus();
    }

    private void loadEmployeeTable() {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (String vectorEmail : empVector) {
            Vector<String> vector = new Vector<>();
            vector.add(empMap.get(vectorEmail).getEmail());
            vector.add(empMap.get(vectorEmail).getFirstName() + " " + empMap.get(vectorEmail).getLastName());
            vector.add(empMap.get(vectorEmail).getDateOfBirth());
            vector.add((empMap.get(vectorEmail).getGenderID() == 1) ? "Male" : "Female");
            vector.add(empMap.get(vectorEmail).getMobile());
            vector.add((empMap.get(vectorEmail).getEmployeeTypeID() == 1) ? "Cashier" : "Stock Manager");
            model.addRow(vector);

        }

    }

    private String validateCustomerField() {

        if (jTextField10.getText().isEmpty()) {
            return "Enter Customer First Name ....";

        } else if (jTextField11.getText().isEmpty()) {
            return "Enter Customer Last Name ....";

        } else if (jTextField12.getText().isEmpty()) {
            return "Enter Customer Email Address ....";

        } else if (!jTextField12.getText().matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            return "Invalid Email Address ....";

        } else if (jTextField13.getText().isEmpty()) {
            return "Enter Customer Mobile Number ....";

        } else if (!jTextField13.getText().matches("^07[1245678]{1}[0-9]{7}$")) {
            return "Invalid Mobile Number ....";

        } else {
            return "Success";

        }

    }

    private void loadCustomers() {
        customerMap.clear();
        customerVector.clear();

        try {

            ResultSet resultSet = MYSQL.Search("SELECT * FROM `customer` ORDER BY `firstName` ASC ");

            while (resultSet.next()) {
                customerVector.add(resultSet.getString("email"));
                Customer customer = new Customer();
                customer.setFirstName(resultSet.getString("firstName"));
                customer.setLastName(resultSet.getString("lastName"));
                customer.setEmail(resultSet.getString("email"));
                customer.setMobile(resultSet.getString("mobile"));
                customerMap.put(resultSet.getString("email"), customer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void resetCustomerFields() {
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        jTextField13.setText("");
        jTextField10.grabFocus();

        jTextField12.setEnabled(true);
        jTable4.setEnabled(true);
        jComboBox6.setEnabled(true);
        jTextField14.setEnabled(true);
        jButton17.setEnabled(true);
        jButton16.setEnabled(false);
        jTextField14.setText("Search By Email Address .... ");
        jTextField14.setForeground(Color.GRAY);
    }

    private void loadCustomerTable() {
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);

        for (String vectorEmail : customerVector) {
            Customer customer = customerMap.get(vectorEmail);
            Vector<String> vector = new Vector<>();
            vector.add(customer.getEmail());
            vector.add(customer.getFirstName());
            vector.add(customer.getLastName());
            vector.add(customer.getMobile());
            model.addRow(vector);
        }

    }

    private void loadBrand() {
        brandMap.clear();
        brandVector.clear();

        try {

            ResultSet resultSet = MYSQL.Search("SELECT * FROM `brand` ");

            while (resultSet.next()) {
                brandVector.add(resultSet.getString("name"));
                brandMap.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadBrandTable() {

        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);

        for (String vectorBrand : brandVector) {
            int brandId = brandMap.get(vectorBrand);
            Vector<String> vector = new Vector<>();
            vector.add(String.valueOf(brandId));
            vector.add(vectorBrand);
            model.addRow(vector);
        }

    }

    private void loadBrandComboBox() {

        Vector<String> vector = new Vector<>();
        vector.add("Select");

        for (String vectorBrand : brandVector) {
            vector.add(vectorBrand);
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
        jComboBox7.setModel(model);

    }

    private void loadWarrentyComboBox() {

        Vector<String> vector = new Vector<>();
        vector.add("Select");

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `warrenty` ");

            while (resultSet.next()) {
                vector.add(resultSet.getString("duration"));
                warrentyVector.add(resultSet.getString("duration"));
                warrentyMap.put(resultSet.getString("duration"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
//            jComboBox3.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadProduct() {
        productMap.clear();
        productVector.clear();

        try {

            ResultSet resultSet = MYSQL.Search("SELECT * FROM `product` INNER JOIN `brand` ON `brand`.`id`=`product`.`brand_id` ORDER BY `product`.`name` ASC");

            while (resultSet.next()) {
                productVector.add(resultSet.getString("id"));
                Product product = new Product();
                product.setPid(resultSet.getString("id"));
                product.setName(resultSet.getString("name"));
                product.setBrandID(resultSet.getString("brand_id"));
                product.setBrandName(resultSet.getString("brand.name"));
                productMap.put(resultSet.getString("id"), product);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadProductTable() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        for (String vectorproduct : productVector) {
            Product product = productMap.get(vectorproduct);
            Vector<String> vector = new Vector<>();
            vector.add(product.getPid());
            vector.add(product.getName());
            vector.add(product.getBrandName());
            model.addRow(vector);
        }
    }

    private void resetBrandFields() {
        jTextField17.setText("");
        jTextField9.setText("Search Brand By Name ....");
        jTextField9.setForeground(Color.GRAY);
        jTextField17.grabFocus();
        jTable3.setEnabled(true);
        jButton7.setEnabled(true);

        jLabel26.setVisible(false);
        jTextField6.setVisible(false);
        jButton11.setEnabled(false);
    }

    private String validateProductFields() {
        if (jTextField4.getText().isEmpty()) {
            return "Enter Product Name ....";

        } else if (jComboBox7.getSelectedIndex() == 0) {
            return "Select Product Brand ....";

        } else {
            return "Success";

        }
    }

    private void resetProductFields() {
        jTextField4.setText("");
        jComboBox7.setSelectedIndex(0);
        jTextField4.grabFocus();
        jComboBox7.setEnabled(true);
        jTextField8.setText("Search Product By Name ....");
        jTextField8.setForeground(Color.GRAY);
        jTable2.setEnabled(true);
        jTextField8.setEnabled(true);
        jComboBox4.setEnabled(true);
        jButton14.setEnabled(true);
        jButton13.setEnabled(false);
        jLabel28.setVisible(false);
        jTextField18.setVisible(false);
    }

    private void loadCompanies() {
        companyMap.clear();
        companyVector.clear();

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `company` ");

            while (resultSet.next()) {
                companyVector.add(resultSet.getString("company_name"));
                companyMap.put(resultSet.getString("company_name"), resultSet.getString("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadCopaniesTable() {
        DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
        model.setRowCount(0);

        for (String vectorName : companyVector) {
            String companyId = companyMap.get(vectorName);
            Vector<String> vector = new Vector<>();
            vector.add(companyId);
            vector.add(vectorName);
            model.addRow(vector);

        }

    }

    private void resetCompanyFields() {
        jTextField20.setText("Search By Name ....");
        jTextField20.setForeground(Color.GRAY);
        jTextField20.setEnabled(true);
        jButton19.setEnabled(false);
        jTable5.setEnabled(true);
        jButton10.setEnabled(true);
        jLabel31.setVisible(false);
        jTextField21.setVisible(false);
        jTextField19.setText("");
        jTextField19.grabFocus();
    }

    private String validateSupplierFields() {
        if (jTextField22.getText().isEmpty()) {
            return "Enter Supplier First Name ....";

        } else if (jTextField23.getText().isEmpty()) {
            return "Enter Supplier Last Name ....";

        } else if (jTextField24.getText().isEmpty()) {
            return "Enter Supplier Mobile Number ....";

        } else if (!jTextField24.getText().matches("^07[1245678]{1}[0-9]{7}$")) {
            return "Invalid Mobile Number ....";

        } else if (jTextField25.getText().isEmpty()) {
            return "Enter Supplier Email Address ....";

        } else if (!jTextField25.getText().matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            return "Invalid Email Address ....";

        } else {
            return "Success";
        }
    }

    private void loadSuppliers() {
        supplierVector.clear();
        supplierMap.clear();

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `supplier` ORDER BY `firstName` ASC");

            while (resultSet.next()) {
                supplierVector.add(resultSet.getString("email"));
                Supplier supplier = new Supplier();
                supplier.setEmail(resultSet.getString("email"));
                supplier.setFirstName(resultSet.getString("firstName"));
                supplier.setLastName(resultSet.getString("lastName"));
                supplier.setMobile(resultSet.getString("mobile"));
                supplierMap.put(resultSet.getString("email"), supplier);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadSuppliersTable() {

        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.setRowCount(0);

        for (String vectorEmail : supplierVector) {
            Supplier supplier = supplierMap.get(vectorEmail);
            Vector<String> vector = new Vector<>();
            vector.add(supplier.getEmail());
            vector.add(supplier.getFirstName());
            vector.add(supplier.getLastName());
            vector.add(supplier.getMobile());
            model.addRow(vector);
        }

    }

    private void resetSupplierFields() {
        jTextField22.setText("");
        jTextField23.setText("");
        jTextField25.setText("");
        jTextField24.setText("");
        jTextField26.setText("Search By Email Address .....");
        jTextField26.setForeground(Color.GRAY);
        jTextField22.grabFocus();
        jTable6.setEnabled(true);
        jTextField25.setEnabled(true);
        jComboBox3.setEnabled(true);
        jTextField26.setEnabled(true);
        jButton22.setEnabled(true);
        jButton21.setEnabled(false);
    }

    private String generateUniqueId() {
        String text = String.valueOf(UUID.randomUUID());
        String[] results = text.split("(-)");
        String id = "#" + results[0];
        return id;

    }

    private void setUniqueStockID() {
        jTextField39.setText(generateUniqueId());
    }

    private void loadWarrentyCombobox() {

        Vector<String> vector = new Vector<>();
        vector.add("Select");

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `warrenty` ");

            while (resultSet.next()) {
                vector.add(resultSet.getString("duration"));
                stockWarrenttyID.put(resultSet.getString("duration"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox13.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadGRNStockTable() {
        DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
        model.setRowCount(0);

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `stock` INNER JOIN `product` ON `product`.`id`=`stock`.`product_id` INNER JOIN `brand` ON `brand`.`id`=`product`.`brand_id` INNER JOIN `warrenty` ON `warrenty`.`id`=`stock`.`warrenty_id` ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("stock.id"));
                vector.add(resultSet.getString("product.id"));
                vector.add(resultSet.getString("product.name"));
                vector.add(resultSet.getString("brand.name"));
                vector.add(resultSet.getString("stock.price"));
                vector.add(resultSet.getString("duration"));
                vector.add(resultSet.getString("stock.qty"));
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private String validateStockManageField() {
        if (jTextField38.getText().isEmpty()) {
            return "Enter Product Quentity ....";

        } else if (!jTextField38.getText().matches("^[1-9]\\d*(\\.\\d+)?$")) {
            return "Invalid Product Quentity ....";

        } else if (jTextField37.getText().isEmpty()) {
            return "Enter Product Selling Price ....";

        } else if (!jTextField37.getText().matches("^[1-9]\\d*(\\.\\d+)?$")) {
            return "Invalid Selling Price ....";

        } else if (jComboBox13.getSelectedIndex() == 0) {
            return "Select Product Warrenty ....";

        } else if (jTextField27.getText().isEmpty()) {
            return "Select Product ....";

        } else {
            return "Success";

        }
    }

    private void resetStockFields() {
        setUniqueStockID();
        jTextField38.setText("0");
        jTextField37.setText("00.00");
        jComboBox13.setSelectedIndex(0);
        jTextField27.setText("");
        jTextField28.setText("");
        jTextField29.setText("");
        jTable7.setEnabled(true);
        jButton32.setEnabled(true);
        jButton31.setEnabled(false);
        jButton33.setEnabled(true);

        jTextField38.grabFocus();

    }

    private String validateGRNFields() {
        if (jTextField31.getText().isEmpty()) {
            return "Select Supplier ....";

        } else if (jTextField44.getText().isEmpty()) {
            return "Select Purchesed Date ....";

        } else if (jTextField32.getText().isEmpty()) {
            return "Select Product ....";

        } else if (jTextField45.getText().isEmpty()) {
            return "Select Company ....";

        } else if (jTextField43.getText().isEmpty()) {
            return "Enter Qty ....";

        } else {
            return "Success";
        }
    }

    private void CalculateGrnTable() {
        for (int i = 0; i < jTable8.getRowCount(); i++) {
            int tqty = Integer.parseInt(String.valueOf(jTable8.getValueAt(i, 7)));
            int unitBuyPrice = Integer.parseInt(String.valueOf(jTable8.getValueAt(i, 5)));
            int totalBuyPrice = tqty * unitBuyPrice;
            totalPrice += totalBuyPrice;
            totalProducts += i;
            totalQty += tqty;

        }

    }

    private void resetGrnStockFields() {

        jTextField32.setText("");
        jTextField35.setText("");
        jTextField41.setText("");
        jTextField42.setText("");
        jTextField40.setText("");
        jTextField33.setText("");
        jTextField36.setText("");
        jTextField43.setText("");

        jTextField33.setEnabled(false);
        jButton35.setVisible(false);
        jButton27.setVisible(true);

    }

    private void resetGrnAllFields() {
        jTextField31.setText("");
        jTextField45.setText("");

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(date);
        jTextField44.setText(today);

        DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
        model.setRowCount(0);

        jTextField30.setText("0");
        jTextField46.setText("0");
        jLabel63.setText("00.00");
    }

    private String validateInvoiceFields() {
        if (jTextField49.getText().isEmpty()) {
            return "Select Product ....";

        } else if (jTextField53.getText().isEmpty()) {
            return "Enter Buy Quentity ....";

        } else if (!jTextField53.getText().matches("^[1-9]\\d*(\\.\\d+)?$")) {
            return "Invalid Product Quentity ....";

        } else {
            return "Success";

        }

    }

    private void loadInvoiceDate() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(date);

        jTextField47.setText(today);
        jTextField57.setText(generateUniqueId());

    }

    private void resetInvoiceAddProductFields() {

        jTextField49.setText("");
        jTextField50.setText("");
        jTextField51.setText("");
        jTextField52.setText("");
        jTextField53.setText("0");

    }

    private void calculateInvoiceTable() {
        for (int i = 0; i < jTable9.getRowCount(); i++) {
            invoiceTotalProducts += i;
            invoiceTotalQty += Integer.parseInt(String.valueOf(jTable9.getValueAt(i, 4)));
            invoiceTotalPrice += (Integer.parseInt(String.valueOf(jTable9.getValueAt(i, 3))) * Integer.parseInt(String.valueOf(jTable9.getValueAt(i, 4))));
        }

        if (jTextField54.getText().equals("0") || jTextField54.getText().isEmpty()) {
            jLabel79.setText(String.valueOf(invoiceTotalPrice));

        } else {

            double percentage = Double.parseDouble(jTextField54.getText());
            double total = Double.parseDouble(String.valueOf(invoiceTotalPrice));

            double GrandTotal = (percentage / 100) * total;
            jLabel79.setText(String.valueOf((total + GrandTotal)));
        }
    }

    private void resetAllInvoiceFields() {

        jTextField57.setText(generateUniqueId());
        
        DefaultTableModel model = (DefaultTableModel) jTable9.getModel();
        model.setRowCount(0);

        jTextField48.setText("Unknown");
        jTextField55.setText("0");
        jTextField56.setText("0");
        jTextField54.setText("0");
        jLabel74.setText("00.0");
        jLabel79.setText("00.0");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        dateChooser3 = new com.raven.datechooser.DateChooser();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        UpdateRow = new javax.swing.JMenuItem();
        DeleteRow = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButtonM7 = new javax.swing.JButton();
        ManageEmployeePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField7 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        ManageProductPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jTextField8 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jTextField9 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jTextField17 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        ManageCustomerPanel = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jTextField14 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        ManageSupplierPanel = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jTextField26 = new javax.swing.JTextField();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        ManageStockPanel = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jTextField34 = new javax.swing.JTextField();
        jButton23 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jButton33 = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox<>();
        jLabel59 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jTextField39 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        ManageGRNPanel = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextField41 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jTextField40 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jTextField43 = new javax.swing.JTextField();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jButton28 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jLabel53 = new javax.swing.JLabel();
        jTextField44 = new javax.swing.JTextField();
        jTextField45 = new javax.swing.JTextField();
        jButton29 = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        ManageInvoicePanel = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jTextField48 = new javax.swing.JTextField();
        jButton36 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jLabel68 = new javax.swing.JLabel();
        jTextField49 = new javax.swing.JTextField();
        jButton37 = new javax.swing.JButton();
        jLabel69 = new javax.swing.JLabel();
        jTextField50 = new javax.swing.JTextField();
        jTextField51 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jTextField52 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jTextField53 = new javax.swing.JTextField();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jTextField54 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jLabel81 = new javax.swing.JLabel();
        jTextField55 = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        jTextField56 = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        jTextField57 = new javax.swing.JTextField();

        dateChooser1.setDateFormat("yyyy-MM-dd");
        dateChooser1.setTextRefernce(jTextField15);

        dateChooser2.setForeground(new java.awt.Color(0, 204, 0));
        dateChooser2.setDateFormat("yyyy-MM-dd");
        dateChooser2.setVerifyInputWhenFocusTarget(false);

        dateChooser3.setDateFormat("yyyy-MM-dd");
        dateChooser3.setTextRefernce(jTextField44);
        dateChooser3.setVerifyInputWhenFocusTarget(false);

        jPopupMenu2.setComponentPopupMenu(jPopupMenu2);
        jPopupMenu2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        UpdateRow.setText("Update");
        UpdateRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateRowActionPerformed(evt);
            }
        });
        jPopupMenu2.add(UpdateRow);

        DeleteRow.setText("Delete");
        DeleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteRowActionPerformed(evt);
            }
        });
        jPopupMenu2.add(DeleteRow);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jPanel3.setBackground(new java.awt.Color(51, 255, 0));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(null);

        jLabel1.setText("Manage Employee");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(60, 10, 100, 16);

        jPanel4.setBackground(new java.awt.Color(51, 255, 0));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });
        jPanel4.setLayout(null);

        jLabel11.setText("Manage Products");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(60, 10, 100, 16);

        jPanel5.setBackground(new java.awt.Color(51, 255, 0));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });
        jPanel5.setLayout(null);

        jLabel27.setText("Manage Customers");
        jPanel5.add(jLabel27);
        jLabel27.setBounds(60, 10, 110, 16);

        jPanel6.setBackground(new java.awt.Color(51, 255, 0));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(null);

        jLabel22.setText("Manage Suppliers");
        jPanel6.add(jLabel22);
        jLabel22.setBounds(60, 10, 110, 16);

        jPanel9.setBackground(new java.awt.Color(51, 255, 0));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(null);

        jLabel51.setText("Manage Stock");
        jPanel9.add(jLabel51);
        jLabel51.setBounds(60, 10, 80, 16);

        jPanel10.setBackground(new java.awt.Color(51, 255, 0));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });
        jPanel10.setLayout(null);

        jLabel52.setText("Manage GRN");
        jPanel10.add(jLabel52);
        jLabel52.setBounds(60, 10, 80, 16);

        jPanel11.setBackground(new java.awt.Color(51, 255, 0));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });
        jPanel11.setLayout(null);

        jLabel83.setText("Manage Invoice");
        jPanel11.add(jLabel83);
        jLabel83.setBounds(60, 10, 90, 16);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton6.setBackground(new java.awt.Color(255, 0, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setText("X");
        jButton6.setBorder(null);
        jButton6.setOpaque(true);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton6MouseExited(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButtonM7.setBackground(new java.awt.Color(153, 153, 153));
        jButtonM7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonM7.setText("---");
        jButtonM7.setBorder(null);
        jButtonM7.setOpaque(true);
        jButtonM7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonM7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonM7MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(591, Short.MAX_VALUE)
                .addComponent(jButtonM7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButtonM7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ManageEmployeePanel.setLayout(null);

        jLabel2.setText("Last Name");
        ManageEmployeePanel.add(jLabel2);
        jLabel2.setBounds(50, 190, 60, 16);

        jLabel3.setText("First Name");
        ManageEmployeePanel.add(jLabel3);
        jLabel3.setBounds(50, 150, 60, 16);
        ManageEmployeePanel.add(jTextField1);
        jTextField1.setBounds(120, 190, 220, 22);

        jLabel4.setText("Gender ");
        ManageEmployeePanel.add(jLabel4);
        jLabel4.setBounds(380, 110, 41, 16);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male ", "Female" }));
        ManageEmployeePanel.add(jComboBox1);
        jComboBox1.setBounds(460, 110, 210, 22);

        jLabel5.setText("Email");
        ManageEmployeePanel.add(jLabel5);
        jLabel5.setBounds(380, 150, 30, 16);
        ManageEmployeePanel.add(jTextField3);
        jTextField3.setBounds(460, 150, 210, 22);

        jLabel6.setText("Date Of Birth");
        ManageEmployeePanel.add(jLabel6);
        jLabel6.setBounds(380, 230, 68, 16);

        jLabel7.setText("Mobile No");
        ManageEmployeePanel.add(jLabel7);
        jLabel7.setBounds(50, 230, 60, 16);
        ManageEmployeePanel.add(jTextField5);
        jTextField5.setBounds(120, 230, 220, 22);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Manage Employees");
        ManageEmployeePanel.add(jLabel9);
        jLabel9.setBounds(40, 20, 180, 22);

        jButton1.setText("Manage Address");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        ManageEmployeePanel.add(jButton1);
        jButton1.setBounds(490, 280, 180, 23);
        jButton1.setVisible(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Email", "Full Name", "Date Of Birth", "Gender", "Mobile No", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        ManageEmployeePanel.add(jScrollPane1);
        jScrollPane1.setBounds(10, 390, 680, 190);
        ManageEmployeePanel.add(jTextField7);
        jTextField7.setBounds(120, 150, 220, 22);
        ManageEmployeePanel.add(jPasswordField1);
        jPasswordField1.setBounds(460, 190, 170, 22);

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        ManageEmployeePanel.add(jButton2);
        jButton2.setBounds(620, 600, 72, 23);

        jButton4.setText("Update");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        ManageEmployeePanel.add(jButton4);
        jButton4.setBounds(530, 600, 75, 23);
        jButton4.setEnabled(false);

        jButton5.setText("Save");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        ManageEmployeePanel.add(jButton5);
        jButton5.setBounds(440, 600, 72, 23);

        jLabel8.setText("Employee Type");
        ManageEmployeePanel.add(jLabel8);
        jLabel8.setBounds(30, 110, 80, 16);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cashier", "Stock Manager" }));
        ManageEmployeePanel.add(jComboBox2);
        jComboBox2.setBounds(120, 110, 220, 22);

        jTextField2.setForeground(new java.awt.Color(102, 102, 102));
        jTextField2.setText("Search By Email Or Mobile No ....");
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        ManageEmployeePanel.add(jTextField2);
        jTextField2.setBounds(470, 350, 220, 22);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Sort By Name");
        ManageEmployeePanel.add(jLabel15);
        jLabel15.setBounds(30, 350, 80, 16);

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ASC", "DESC" }));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });
        ManageEmployeePanel.add(jComboBox5);
        jComboBox5.setBounds(120, 350, 180, 22);

        jLabel23.setText("Password");
        ManageEmployeePanel.add(jLabel23);
        jLabel23.setBounds(380, 190, 50, 16);
        ManageEmployeePanel.add(jTextField15);
        jTextField15.setBounds(460, 230, 170, 22);

        jButton3.setText("...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        ManageEmployeePanel.add(jButton3);
        jButton3.setBounds(640, 230, 30, 23);

        jLabel24.setText("Join Date");
        ManageEmployeePanel.add(jLabel24);
        jLabel24.setBounds(490, 60, 60, 16);
        jLabel24.setVisible(false);

        jTextField16.setEnabled(false);
        ManageEmployeePanel.add(jTextField16);
        jTextField16.setBounds(560, 60, 110, 22);
        jTextField16.setVisible(false);

        jButton18.setText("...");
        jButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton18MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton18MouseReleased(evt);
            }
        });
        ManageEmployeePanel.add(jButton18);
        jButton18.setBounds(640, 190, 30, 23);

        ManageProductPanel.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Manage Products");
        ManageProductPanel.add(jLabel10);
        jLabel10.setBounds(20, 250, 157, 22);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Brand"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        ManageProductPanel.add(jScrollPane2);
        jScrollPane2.setBounds(10, 400, 680, 180);

        jLabel12.setText("Product Name");
        ManageProductPanel.add(jLabel12);
        jLabel12.setBounds(20, 320, 90, 16);
        ManageProductPanel.add(jTextField4);
        jTextField4.setBounds(110, 320, 280, 22);

        jLabel13.setText("Product Brand");
        ManageProductPanel.add(jLabel13);
        jLabel13.setBounds(20, 120, 90, 16);

        jButton7.setText("Add");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        ManageProductPanel.add(jButton7);
        jButton7.setBounds(110, 160, 100, 23);

        jButton8.setText("Cancel");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        ManageProductPanel.add(jButton8);
        jButton8.setBounds(110, 190, 210, 23);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Brand"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        ManageProductPanel.add(jScrollPane3);
        jScrollPane3.setBounds(340, 80, 330, 140);

        jButton11.setText("Update");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        ManageProductPanel.add(jButton11);
        jButton11.setBounds(220, 160, 100, 23);
        jButton11.setEnabled(false);

        jTextField8.setForeground(new java.awt.Color(102, 102, 102));
        jTextField8.setText("Search Product By Name ....");
        jTextField8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField8FocusLost(evt);
            }
        });
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField8KeyReleased(evt);
            }
        });
        ManageProductPanel.add(jTextField8);
        jTextField8.setBounds(500, 360, 190, 22);

        jButton12.setText("Cancel");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        ManageProductPanel.add(jButton12);
        jButton12.setBounds(610, 600, 72, 23);

        jButton13.setText("Update");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        ManageProductPanel.add(jButton13);
        jButton13.setBounds(520, 600, 72, 23);
        jButton13.setEnabled(false);

        jButton14.setText("Save");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        ManageProductPanel.add(jButton14);
        jButton14.setBounds(420, 600, 81, 23);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Sort by Name");
        ManageProductPanel.add(jLabel14);
        jLabel14.setBounds(270, 360, 80, 16);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ASC", "DESC" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
        ManageProductPanel.add(jComboBox4);
        jComboBox4.setBounds(360, 360, 120, 22);

        jTextField9.setForeground(new java.awt.Color(102, 102, 102));
        jTextField9.setText("Search Brand By Name ....");
        jTextField9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField9FocusLost(evt);
            }
        });
        jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField9KeyReleased(evt);
            }
        });
        ManageProductPanel.add(jTextField9);
        jTextField9.setBounds(460, 50, 210, 22);

        jLabel25.setText("Product Brand");
        ManageProductPanel.add(jLabel25);
        jLabel25.setBounds(420, 320, 90, 16);

        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });
        ManageProductPanel.add(jComboBox7);
        jComboBox7.setBounds(510, 320, 180, 22);
        ManageProductPanel.add(jTextField17);
        jTextField17.setBounds(110, 120, 220, 22);
        ManageProductPanel.add(jTextField6);
        jTextField6.setBounds(260, 80, 64, 22);
        jTextField6.setVisible(false);
        jTextField6.setEnabled(false);

        jLabel26.setText("ID");
        ManageProductPanel.add(jLabel26);
        jLabel26.setBounds(240, 80, 20, 16);
        jLabel26.setVisible(false);

        jLabel28.setText("Product ID");
        ManageProductPanel.add(jLabel28);
        jLabel28.setBounds(540, 280, 60, 16);
        jLabel28.setVisible(false);

        jTextField18.setEnabled(false);
        ManageProductPanel.add(jTextField18);
        jTextField18.setBounds(610, 280, 80, 22);
        jTextField18.setVisible(false);

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel38.setText("Manage Brands");
        ManageProductPanel.add(jLabel38);
        jLabel38.setBounds(19, 19, 140, 22);

        ManageProductPanel.setVisible(false);

        ManageCustomerPanel.setLayout(null);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("Manage Customer ");
        ManageCustomerPanel.add(jLabel16);
        jLabel16.setBounds(37, 27, 169, 22);

        jLabel17.setText("First Name ");
        ManageCustomerPanel.add(jLabel17);
        jLabel17.setBounds(40, 90, 60, 16);

        jLabel18.setText("Last Name ");
        ManageCustomerPanel.add(jLabel18);
        jLabel18.setBounds(40, 130, 60, 16);

        jLabel19.setText("Email");
        ManageCustomerPanel.add(jLabel19);
        jLabel19.setBounds(40, 170, 60, 16);

        jLabel20.setText("Mobile");
        ManageCustomerPanel.add(jLabel20);
        jLabel20.setBounds(40, 210, 37, 16);
        ManageCustomerPanel.add(jTextField10);
        jTextField10.setBounds(120, 90, 210, 22);
        ManageCustomerPanel.add(jTextField11);
        jTextField11.setBounds(120, 130, 210, 22);
        ManageCustomerPanel.add(jTextField12);
        jTextField12.setBounds(120, 170, 210, 22);
        ManageCustomerPanel.add(jTextField13);
        jTextField13.setBounds(120, 210, 210, 22);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Email", "First Name", "Last Name", "Mobile"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        ManageCustomerPanel.add(jScrollPane4);
        jScrollPane4.setBounds(10, 330, 680, 240);

        jButton15.setText("Cancel");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        ManageCustomerPanel.add(jButton15);
        jButton15.setBounds(610, 600, 72, 23);

        jButton16.setText("Update");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        ManageCustomerPanel.add(jButton16);
        jButton16.setBounds(520, 600, 72, 23);
        jButton16.setEnabled(false);

        jButton17.setText("Save");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        ManageCustomerPanel.add(jButton17);
        jButton17.setBounds(420, 600, 81, 23);

        jTextField14.setForeground(new java.awt.Color(102, 102, 102));
        jTextField14.setText("Search By Email Address .... ");
        jTextField14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField14FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField14FocusLost(evt);
            }
        });
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
        });
        ManageCustomerPanel.add(jTextField14);
        jTextField14.setBounds(477, 290, 210, 22);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Sort By Name ");
        ManageCustomerPanel.add(jLabel21);
        jLabel21.setBounds(40, 290, 80, 16);

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ASC", "DESC" }));
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });
        ManageCustomerPanel.add(jComboBox6);
        jComboBox6.setBounds(130, 290, 200, 22);

        ManageCustomerPanel.setVisible(false);

        ManageSupplierPanel.setLayout(null);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel29.setText("Manage Suppliers");
        ManageSupplierPanel.add(jLabel29);
        jLabel29.setBounds(30, 230, 190, 22);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        ManageSupplierPanel.add(jScrollPane5);
        jScrollPane5.setBounds(400, 70, 240, 140);

        jLabel30.setText("Company Name ");
        ManageSupplierPanel.add(jLabel30);
        jLabel30.setBounds(40, 110, 90, 16);
        ManageSupplierPanel.add(jTextField19);
        jTextField19.setBounds(140, 110, 180, 22);

        jTextField20.setForeground(new java.awt.Color(102, 102, 102));
        jTextField20.setText("Search By Name ....");
        jTextField20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField20FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField20FocusLost(evt);
            }
        });
        jTextField20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField20KeyReleased(evt);
            }
        });
        ManageSupplierPanel.add(jTextField20);
        jTextField20.setBounds(490, 40, 150, 22);

        jLabel31.setText("ID");
        ManageSupplierPanel.add(jLabel31);
        jLabel31.setBounds(220, 80, 20, 16);
        jLabel31.setVisible(false);
        ManageSupplierPanel.add(jTextField21);
        jTextField21.setBounds(240, 80, 77, 22);
        jTextField21.setEnabled(false);
        jTextField21.setVisible(false);

        jButton9.setText("Cancel");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        ManageSupplierPanel.add(jButton9);
        jButton9.setBounds(140, 170, 180, 23);

        jButton10.setText("Add New");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        ManageSupplierPanel.add(jButton10);
        jButton10.setBounds(140, 140, 90, 23);

        jButton19.setText("Update");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        ManageSupplierPanel.add(jButton19);
        jButton19.setBounds(240, 140, 80, 23);
        jButton19.setEnabled(false);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setText("Manage Companies");
        ManageSupplierPanel.add(jLabel32);
        jLabel32.setBounds(30, 20, 190, 22);

        jLabel33.setText("First Name");
        ManageSupplierPanel.add(jLabel33);
        jLabel33.setBounds(20, 280, 70, 16);
        ManageSupplierPanel.add(jTextField22);
        jTextField22.setBounds(20, 300, 140, 22);
        ManageSupplierPanel.add(jTextField23);
        jTextField23.setBounds(180, 300, 140, 22);

        jLabel34.setText("Last Name");
        ManageSupplierPanel.add(jLabel34);
        jLabel34.setBounds(180, 280, 70, 16);

        jLabel35.setText("Mobile No");
        ManageSupplierPanel.add(jLabel35);
        jLabel35.setBounds(350, 280, 70, 16);
        ManageSupplierPanel.add(jTextField24);
        jTextField24.setBounds(350, 300, 140, 22);

        jLabel36.setText("Email");
        ManageSupplierPanel.add(jLabel36);
        jLabel36.setBounds(510, 280, 70, 16);
        ManageSupplierPanel.add(jTextField25);
        jTextField25.setBounds(510, 300, 180, 22);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Email", "first Name", "Last Name", "Mobile No"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTable6);

        ManageSupplierPanel.add(jScrollPane6);
        jScrollPane6.setBounds(10, 380, 680, 200);

        jTextField26.setForeground(new java.awt.Color(102, 102, 102));
        jTextField26.setText("Search By Email Address .....");
        jTextField26.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField26FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField26FocusLost(evt);
            }
        });
        jTextField26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField26KeyReleased(evt);
            }
        });
        ManageSupplierPanel.add(jTextField26);
        jTextField26.setBounds(510, 350, 180, 22);

        jButton20.setText("Cancel");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        ManageSupplierPanel.add(jButton20);
        jButton20.setBounds(600, 600, 81, 23);

        jButton21.setText("Update");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        ManageSupplierPanel.add(jButton21);
        jButton21.setBounds(510, 600, 81, 23);
        jButton21.setEnabled(false);

        jButton22.setText("Save");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        ManageSupplierPanel.add(jButton22);
        jButton22.setBounds(420, 600, 81, 23);

        jLabel37.setText("Sort By Name");
        ManageSupplierPanel.add(jLabel37);
        jLabel37.setBounds(230, 350, 80, 16);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ASC", "DESC" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        ManageSupplierPanel.add(jComboBox3);
        jComboBox3.setBounds(310, 350, 180, 22);

        ManageStockPanel.setLayout(null);

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel39.setText("Manage Product Stock");
        ManageStockPanel.add(jLabel39);
        jLabel39.setBounds(20, 20, 210, 22);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock ID", "Product_ID", "Product Name", "Brand Name", "Sell Price", "Warrenty", "Quentity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTable7);

        ManageStockPanel.add(jScrollPane7);
        jScrollPane7.setBounds(10, 270, 690, 260);

        jTextField34.setForeground(new java.awt.Color(102, 102, 102));
        jTextField34.setText("Search By Product Name ....");
        jTextField34.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField34FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField34FocusLost(evt);
            }
        });
        jTextField34.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField34KeyReleased(evt);
            }
        });
        ManageStockPanel.add(jTextField34);
        jTextField34.setBounds(520, 240, 180, 22);

        jButton23.setText("Cancel");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        ManageStockPanel.add(jButton23);
        jButton23.setBounds(610, 580, 72, 23);

        jButton31.setText("Update");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        ManageStockPanel.add(jButton31);
        jButton31.setBounds(530, 580, 72, 23);
        jButton31.setEnabled(false);

        jButton32.setText("Save");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        ManageStockPanel.add(jButton32);
        jButton32.setBounds(450, 580, 72, 23);

        jLabel55.setText("Product Name");
        ManageStockPanel.add(jLabel55);
        jLabel55.setBounds(390, 80, 80, 16);

        jButton33.setText("...");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        ManageStockPanel.add(jButton33);
        jButton33.setBounds(540, 80, 20, 23);

        jLabel57.setText("Sell Price");
        ManageStockPanel.add(jLabel57);
        jLabel57.setBounds(20, 140, 60, 16);

        jTextField37.setText("00.00");
        ManageStockPanel.add(jTextField37);
        jTextField37.setBounds(110, 140, 140, 22);

        jLabel58.setText("Warrenty");
        ManageStockPanel.add(jLabel58);
        jLabel58.setBounds(20, 170, 50, 16);

        ManageStockPanel.add(jComboBox13);
        jComboBox13.setBounds(110, 170, 200, 22);

        jLabel59.setText("Quentity");
        ManageStockPanel.add(jLabel59);
        jLabel59.setBounds(20, 110, 46, 16);

        jTextField38.setText("0");
        ManageStockPanel.add(jTextField38);
        jTextField38.setBounds(110, 110, 140, 22);

        jLabel60.setText("Stock ID");
        ManageStockPanel.add(jLabel60);
        jLabel60.setBounds(20, 80, 43, 16);

        jTextField39.setEnabled(false);
        ManageStockPanel.add(jTextField39);
        jTextField39.setBounds(110, 80, 110, 22);

        jTextField27.setEnabled(false);
        ManageStockPanel.add(jTextField27);
        jTextField27.setBounds(480, 80, 50, 22);

        jTextField28.setEnabled(false);
        ManageStockPanel.add(jTextField28);
        jTextField28.setBounds(480, 110, 160, 22);

        jTextField29.setEnabled(false);
        ManageStockPanel.add(jTextField29);
        jTextField29.setBounds(480, 140, 160, 22);

        jLabel40.setText("LKR");
        ManageStockPanel.add(jLabel40);
        jLabel40.setBounds(260, 140, 20, 20);

        ManageStockPanel.setVisible(false);

        ManageGRNPanel.setLayout(null);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel41.setText("Manage Good Recived Note ");
        ManageGRNPanel.add(jLabel41);
        jLabel41.setBounds(20, 20, 260, 22);

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel42.setText("Select Supplier");
        ManageGRNPanel.add(jLabel42);
        jLabel42.setBounds(30, 70, 90, 16);

        jTextField31.setEnabled(false);
        ManageGRNPanel.add(jTextField31);
        jTextField31.setBounds(30, 90, 140, 22);

        jButton24.setText("...");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        ManageGRNPanel.add(jButton24);
        jButton24.setBounds(170, 90, 20, 23);

        jButton25.setText("...");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        ManageGRNPanel.add(jButton25);
        jButton25.setBounds(530, 90, 20, 23);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setLayout(null);

        jLabel44.setText("Name");
        jPanel7.add(jLabel44);
        jLabel44.setBounds(140, 30, 40, 16);

        jTextField35.setEnabled(false);
        jPanel7.add(jTextField35);
        jTextField35.setBounds(180, 30, 150, 22);

        jLabel46.setText("Warrenty");
        jPanel7.add(jLabel46);
        jLabel46.setBounds(490, 30, 50, 16);

        jTextField42.setEnabled(false);
        jPanel7.add(jTextField42);
        jTextField42.setBounds(550, 30, 130, 22);

        jLabel45.setText("Brand");
        jPanel7.add(jLabel45);
        jLabel45.setBounds(340, 30, 40, 16);

        jTextField41.setEnabled(false);
        jPanel7.add(jTextField41);
        jTextField41.setBounds(380, 30, 100, 22);

        jLabel49.setText("Quentity");
        jPanel7.add(jLabel49);
        jLabel49.setBounds(10, 70, 50, 16);

        jTextField40.setEnabled(false);
        jPanel7.add(jTextField40);
        jTextField40.setBounds(60, 70, 70, 22);

        jTextField36.setEnabled(false);
        jPanel7.add(jTextField36);
        jTextField36.setBounds(390, 70, 77, 22);

        jLabel48.setText("Sell Price");
        jPanel7.add(jLabel48);
        jLabel48.setBounds(330, 70, 50, 16);

        jTextField33.setEnabled(false);
        jPanel7.add(jTextField33);
        jTextField33.setBounds(220, 70, 77, 22);

        jLabel47.setText("Buy Price");
        jPanel7.add(jLabel47);
        jLabel47.setBounds(160, 70, 60, 16);

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel50.setText("Add Quentity");
        jPanel7.add(jLabel50);
        jLabel50.setBounds(490, 70, 80, 16);
        jPanel7.add(jTextField43);
        jTextField43.setBounds(580, 70, 100, 22);

        jButton26.setText("Cancel");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton26);
        jButton26.setBounds(610, 110, 72, 23);

        jButton27.setText("Add");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton27);
        jButton27.setBounds(530, 110, 70, 23);

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setText("Select Stock");
        jPanel7.add(jLabel43);
        jLabel43.setBounds(10, 10, 70, 16);

        jTextField32.setEnabled(false);
        jPanel7.add(jTextField32);
        jTextField32.setBounds(10, 30, 90, 22);

        jButton28.setText("...");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton28);
        jButton28.setBounds(100, 30, 20, 23);

        jButton35.setText("Update");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton35);
        jButton35.setBounds(529, 110, 72, 23);
        jButton35.setVisible(false);

        ManageGRNPanel.add(jPanel7);
        jPanel7.setBounds(10, 130, 690, 150);

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock_ID", "Name", "Brand", "Warrenty", "Qty", "Buy Price", "Sell Price", "Add Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable8MouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(jTable8);

        ManageGRNPanel.add(jScrollPane8);
        jScrollPane8.setBounds(10, 320, 690, 190);

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel53.setText("Select Date");
        ManageGRNPanel.add(jLabel53);
        jLabel53.setBounds(410, 70, 70, 16);

        jTextField44.setEnabled(false);
        ManageGRNPanel.add(jTextField44);
        jTextField44.setBounds(410, 90, 120, 22);

        jTextField45.setEnabled(false);
        ManageGRNPanel.add(jTextField45);
        jTextField45.setBounds(220, 90, 140, 22);

        jButton29.setText("...");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        ManageGRNPanel.add(jButton29);
        jButton29.setBounds(360, 90, 20, 23);

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel54.setText("Select Company");
        ManageGRNPanel.add(jLabel54);
        jLabel54.setBounds(220, 70, 90, 16);

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setText("Total Products");
        ManageGRNPanel.add(jLabel56);
        jLabel56.setBounds(10, 540, 100, 20);

        jTextField30.setText("0");
        jTextField30.setEnabled(false);
        ManageGRNPanel.add(jTextField30);
        jTextField30.setBounds(120, 540, 70, 22);

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setText("Total Quentity");
        ManageGRNPanel.add(jLabel61);
        jLabel61.setBounds(210, 540, 100, 20);

        jTextField46.setText("0");
        jTextField46.setEnabled(false);
        ManageGRNPanel.add(jTextField46);
        jTextField46.setBounds(320, 540, 70, 22);

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 153));
        jLabel62.setText("Total Price :");
        ManageGRNPanel.add(jLabel62);
        jLabel62.setBounds(430, 540, 110, 25);

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel63.setText("00.00");
        ManageGRNPanel.add(jLabel63);
        jLabel63.setBounds(540, 540, 110, 25);

        jButton30.setText("Cancel");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        ManageGRNPanel.add(jButton30);
        jButton30.setBounds(610, 610, 72, 23);

        jButton34.setText("Save");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });
        ManageGRNPanel.add(jButton34);
        jButton34.setBounds(530, 610, 72, 23);

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel64.setText("LKR");
        ManageGRNPanel.add(jLabel64);
        jLabel64.setBounds(660, 536, 40, 30);

        ManageGRNPanel.setVisible(false);

        ManageInvoicePanel.setLayout(null);

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel65.setText("Manage Invoice ");
        ManageInvoicePanel.add(jLabel65);
        jLabel65.setBounds(30, 20, 150, 22);

        jLabel66.setText("Date");
        ManageInvoicePanel.add(jLabel66);
        jLabel66.setBounds(220, 80, 30, 16);

        jTextField47.setEnabled(false);
        ManageInvoicePanel.add(jTextField47);
        jTextField47.setBounds(250, 80, 120, 22);

        jLabel67.setText("customer ");
        ManageInvoicePanel.add(jLabel67);
        jLabel67.setBounds(390, 80, 60, 16);

        jTextField48.setText("Unknown");
        jTextField48.setEnabled(false);
        ManageInvoicePanel.add(jTextField48);
        jTextField48.setBounds(450, 80, 160, 22);

        jButton36.setText("...");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });
        ManageInvoicePanel.add(jButton36);
        jButton36.setBounds(610, 80, 20, 23);

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock_ID", "Product", "Brand", "Unit Price", "Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(jTable9);

        ManageInvoicePanel.add(jScrollPane9);
        jScrollPane9.setBounds(20, 240, 670, 180);

        jLabel68.setText("Stock");
        ManageInvoicePanel.add(jLabel68);
        jLabel68.setBounds(20, 130, 30, 16);

        jTextField49.setEnabled(false);
        ManageInvoicePanel.add(jTextField49);
        jTextField49.setBounds(20, 150, 90, 22);

        jButton37.setText("...");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        ManageInvoicePanel.add(jButton37);
        jButton37.setBounds(110, 150, 20, 23);

        jLabel69.setText("Product ");
        ManageInvoicePanel.add(jLabel69);
        jLabel69.setBounds(150, 130, 50, 16);

        jTextField50.setEnabled(false);
        ManageInvoicePanel.add(jTextField50);
        jTextField50.setBounds(150, 150, 120, 20);

        jTextField51.setEnabled(false);
        ManageInvoicePanel.add(jTextField51);
        jTextField51.setBounds(290, 150, 120, 20);

        jLabel70.setText(" Brand");
        ManageInvoicePanel.add(jLabel70);
        jLabel70.setBounds(290, 130, 40, 16);

        jLabel71.setText("Unit Price");
        ManageInvoicePanel.add(jLabel71);
        jLabel71.setBounds(430, 130, 60, 16);

        jTextField52.setEnabled(false);
        ManageInvoicePanel.add(jTextField52);
        jTextField52.setBounds(430, 150, 120, 20);

        jLabel72.setText("Quentity");
        ManageInvoicePanel.add(jLabel72);
        jLabel72.setBounds(570, 130, 60, 16);
        ManageInvoicePanel.add(jTextField53);
        jTextField53.setBounds(570, 150, 120, 20);

        jButton38.setText("Cancel");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        ManageInvoicePanel.add(jButton38);
        jButton38.setBounds(620, 190, 72, 23);

        jButton39.setText("Add");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });
        ManageInvoicePanel.add(jButton39);
        jButton39.setBounds(540, 190, 72, 23);

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel73.setText("Total Price :");
        ManageInvoicePanel.add(jLabel73);
        jLabel73.setBounds(440, 450, 80, 20);

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel74.setText("00.0");
        ManageInvoicePanel.add(jLabel74);
        jLabel74.setBounds(530, 450, 110, 20);

        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel75.setText("LKR");
        ManageInvoicePanel.add(jLabel75);
        jLabel75.setBounds(640, 450, 30, 20);

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel76.setText("Discount  :");
        ManageInvoicePanel.add(jLabel76);
        jLabel76.setBounds(450, 490, 70, 20);

        jTextField54.setText("0");
        jTextField54.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField54KeyReleased(evt);
            }
        });
        ManageInvoicePanel.add(jTextField54);
        jTextField54.setBounds(530, 490, 64, 22);

        jLabel77.setText("%");
        ManageInvoicePanel.add(jLabel77);
        jLabel77.setBounds(600, 490, 10, 20);

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 0, 153));
        jLabel78.setText("Grand Total :");
        ManageInvoicePanel.add(jLabel78);
        jLabel78.setBounds(410, 530, 120, 25);

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 153));
        jLabel79.setText("00.0");
        ManageInvoicePanel.add(jLabel79);
        jLabel79.setBounds(530, 530, 110, 25);

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 153));
        jLabel80.setText("LKR");
        ManageInvoicePanel.add(jLabel80);
        jLabel80.setBounds(640, 530, 40, 25);

        jButton40.setText("Cancel");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        ManageInvoicePanel.add(jButton40);
        jButton40.setBounds(610, 590, 72, 23);

        jButton41.setText("Make Payment & Print Report");
        ManageInvoicePanel.add(jButton41);
        jButton41.setBounds(390, 590, 200, 23);

        jLabel81.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel81.setText("Item Count");
        ManageInvoicePanel.add(jLabel81);
        jLabel81.setBounds(20, 460, 70, 16);

        jTextField55.setText("0");
        jTextField55.setEnabled(false);
        ManageInvoicePanel.add(jTextField55);
        jTextField55.setBounds(90, 460, 100, 22);

        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel82.setText("Qty Count");
        ManageInvoicePanel.add(jLabel82);
        jLabel82.setBounds(20, 500, 70, 16);

        jTextField56.setText("0");
        jTextField56.setEnabled(false);
        ManageInvoicePanel.add(jTextField56);
        jTextField56.setBounds(90, 500, 100, 22);

        jLabel84.setText("Invoice ID");
        ManageInvoicePanel.add(jLabel84);
        jLabel84.setBounds(20, 80, 60, 16);

        jTextField57.setEnabled(false);
        ManageInvoicePanel.add(jTextField57);
        jTextField57.setBounds(80, 80, 120, 22);

        ManageInvoicePanel.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ManageEmployeePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 22, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 214, Short.MAX_VALUE)
                    .addComponent(ManageProductPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 214, Short.MAX_VALUE)
                    .addComponent(ManageCustomerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 214, Short.MAX_VALUE)
                    .addComponent(ManageSupplierPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 214, Short.MAX_VALUE)
                    .addComponent(ManageStockPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 214, Short.MAX_VALUE)
                    .addComponent(ManageGRNPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 214, Short.MAX_VALUE)
                    .addComponent(ManageInvoicePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(ManageEmployeePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 32, Short.MAX_VALUE)
                    .addComponent(ManageProductPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 30, Short.MAX_VALUE)
                    .addComponent(ManageCustomerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 32, Short.MAX_VALUE)
                    .addComponent(ManageSupplierPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 32, Short.MAX_VALUE)
                    .addComponent(ManageStockPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 32, Short.MAX_VALUE)
                    .addComponent(ManageGRNPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 32, Short.MAX_VALUE)
                    .addComponent(ManageInvoicePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        ManageSupplierPanel.setVisible(false);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        jTextField2.setText("");
        jTextField2.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        if (jTextField2.getText().isEmpty()) {
            jTextField2.setText("Search By Email Or Mobile No ....");
            jTextField2.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_jTextField2FocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String email = jTextField3.getText();

        EmployeeAddress employeeAddress = new EmployeeAddress(this, true, email);
        employeeAddress.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseEntered
        jButton6.setBackground(Color.red);
        jButton6.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton6MouseEntered

    private void jButton6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseExited
        jButton6.setBackground(Color.red);
        jButton6.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButton6MouseExited

    private void jButtonM7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonM7MouseExited
        jButtonM7.setBackground(Color.GRAY);
        jButtonM7.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonM7MouseExited

    private void jButtonM7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonM7MouseEntered
        jButtonM7.setBackground(Color.BLACK);
        jButtonM7.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonM7MouseEntered

    private void jTextField8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusGained
        jTextField8.setText("");
        jTextField8.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField8FocusGained

    private void jTextField8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusLost
        if (jTextField8.getText().isEmpty()) {
            jTextField8.setText("Enter New Brand Name ....");
            jTextField8.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_jTextField8FocusLost

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        ManageEmployeePanel.setVisible(false);
        ManageGRNPanel.setVisible(false);
        ManageSupplierPanel.setVisible(false);
        ManageStockPanel.setVisible(false);
        ManageCustomerPanel.setVisible(false);
        ManageInvoicePanel.setVisible(false);
        ManageProductPanel.setVisible(true);
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        ManageEmployeePanel.setVisible(false);
        ManageGRNPanel.setVisible(false);
        ManageSupplierPanel.setVisible(false);
        ManageStockPanel.setVisible(false);
        ManageProductPanel.setVisible(false);
        ManageInvoicePanel.setVisible(false);
        ManageCustomerPanel.setVisible(true);
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        ManageProductPanel.setVisible(false);
        ManageGRNPanel.setVisible(false);
        ManageSupplierPanel.setVisible(false);
        ManageStockPanel.setVisible(false);
        ManageCustomerPanel.setVisible(false);
        ManageInvoicePanel.setVisible(false);
        ManageEmployeePanel.setVisible(true);
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String empType = String.valueOf(jComboBox2.getSelectedItem());
        String fname = jTextField7.getText();
        String lname = jTextField1.getText();
        String mobile = jTextField5.getText();
        String gender = String.valueOf(jComboBox1.getSelectedItem());
        String email = jTextField3.getText();
        String password = String.valueOf(jPasswordField1.getPassword());
        String dob = jTextField15.getText();

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(date);
        boolean isFound = false;

        String Status = validateEmployeeFields();

        if (Status == "Success") {

            for (String vectorEmail : empVector) {
                if (vectorEmail.equals(email)) {
                    isFound = true;
                    break;
                }

            }

            if (!isFound) {
                int empId = (empType.equals("Cashier")) ? 1 : 2;
                int genderID = (gender.equals("Male")) ? 1 : 2;
                MYSQL.Iud("INSERT INTO `employee` (`email`,`firstName`,`lastName`,`password`,`mobile`,`employee_type_id`,`gender_id`,`dob`,`join_date`) VALUES ('" + email + "','" + fname + "','" + lname + "','" + password + "','" + mobile + "','" + empId + "','" + genderID + "','" + dob + "','" + today + "') ");

                resetEmployeeFields();
                loadEmployees();
                loadEmployeeTable();
            }

        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dateChooser1.showPopup();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            int selectedRowIndex = jTable1.getSelectedRow();

            if (selectedRowIndex != -1) {
                Employee employee = empMap.get(String.valueOf(jTable1.getValueAt(selectedRowIndex, 0)));
                jComboBox2.setSelectedIndex(employee.getEmployeeTypeID());
                jTextField7.setText(employee.getFirstName());
                jTextField1.setText(employee.getLastName());
                jTextField5.setText(employee.getMobile());
                jComboBox1.setSelectedIndex(employee.getGenderID());
                jTextField3.setText(employee.getEmail());
                jPasswordField1.setText(employee.getPassword());
                jTextField15.setText(employee.getDateOfBirth());
                jTextField16.setText(employee.getJoinDate());
                jTextField3.setText(String.valueOf(jTable1.getValueAt(selectedRowIndex, 0)));

                jTable1.setEnabled(false);

                jComboBox5.setEnabled(false);
                jTextField2.setEnabled(false);
                jComboBox1.setEnabled(false);
                jTextField3.setEnabled(false);
                jTextField15.setEnabled(false);

                jButton5.setEnabled(false);
                jButton4.setEnabled(true);
                jButton1.setVisible(true);
                jTextField16.setVisible(true);
                jLabel24.setVisible(true);

            }

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        resetEmployeeFields();
        loadEmployeeTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton18MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton18MousePressed
        jPasswordField1.setEchoChar((char) 0);
    }//GEN-LAST:event_jButton18MousePressed

    private void jButton18MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton18MouseReleased
        jPasswordField1.setEchoChar('\u2022');
    }//GEN-LAST:event_jButton18MouseReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        boolean isFound = false;
        String status = validateEmployeeFields();

        if (status.equals("Success")) {

            int empType = jComboBox2.getSelectedIndex();
            String fname = jTextField7.getText();
            String lname = jTextField1.getText();
            String mobile = jTextField5.getText();
            String email = jTextField3.getText();
            String password = String.valueOf(jPasswordField1.getPassword());

            for (String vectorEmail : empVector) {
                Employee employee = empMap.get(vectorEmail);
                if (employee.getMobile().equals(mobile) && !employee.getEmail().equals(email)) {
                    isFound = true;
                    break;
                }

            }

            if (!isFound) {
                MYSQL.Iud("UPDATE `employee` SET `firstName`='" + fname + "',`lastName`='" + lname + "',`mobile`='" + mobile + "',`password`='" + password + "',`employee_type_id`='" + empType + "' WHERE `email`='" + email + "' ");
                loadEmployees();
                loadEmployeeTable();
                resetEmployeeFields();

            } else {
                Alert("This Mobile Number Already Exist ....");
            }

        } else {
            Alert(status);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased

        String order = null;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        if (jComboBox5.getSelectedIndex() == 0) {
            order = "ASC";
        } else {
            order = "DESC";
        }

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `employee` WHERE `email` LIKE '%" + jTextField2.getText() + "%' ORDER BY `firstName` " + order + " ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
                vector.add(resultSet.getString("dob"));
                vector.add((resultSet.getInt("gender_id") == 1) ? "Male" : "Female");
                vector.add(resultSet.getString("mobile"));
                vector.add((resultSet.getInt("employee_type_id") == 1) ? "Cashier" : "Stock Manager");
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        String order = null;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        if (jComboBox5.getSelectedIndex() == 0) {
            order = "ASC";
        } else {
            order = "DESC";
        }

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `employee` ORDER BY `firstName` " + order + " ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
                vector.add(resultSet.getString("dob"));
                vector.add((resultSet.getInt("gender_id") == 1) ? "Male" : "Female");
                vector.add(resultSet.getString("mobile"));
                vector.add((resultSet.getInt("employee_type_id") == 1) ? "Cashier" : "Stock Manager");
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        String status = validateCustomerField();

        if (status.equals("Success")) {

            String fname = jTextField10.getText();
            String lname = jTextField11.getText();
            String email = jTextField12.getText();
            String mobile = jTextField13.getText();
            boolean isFound = false;

            for (String vectorEmail : customerVector) {
                if (vectorEmail.equals(email)) {
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                MYSQL.Iud("INSERT INTO `customer`(`firstName`,`lastName`,`email`,`mobile`) VALUES ('" + fname + "','" + lname + "','" + email + "','" + mobile + "') ");
                resetCustomerFields();
                loadCustomers();
                loadCustomerTable();

            } else {
                Alert("This Customer Already Exist ....");
            }

        } else {
            Alert(status);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        if (evt.getClickCount() == 2) {
            int selectedRow = jTable4.getSelectedRow();

            if (selectedRow != -1) {
                jTextField10.setText(String.valueOf(jTable4.getValueAt(selectedRow, 1)));
                jTextField11.setText(String.valueOf(jTable4.getValueAt(selectedRow, 2)));
                jTextField12.setText(String.valueOf(jTable4.getValueAt(selectedRow, 0)));
                jTextField13.setText(String.valueOf(jTable4.getValueAt(selectedRow, 3)));

                jTextField12.setEnabled(false);
                jTable4.setEnabled(false);
                jComboBox6.setEnabled(false);
                jTextField14.setEnabled(false);
                jButton17.setEnabled(false);
                jButton16.setEnabled(true);

            }

        }
    }//GEN-LAST:event_jTable4MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        resetCustomerFields();
        loadCustomerTable();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        String status = validateCustomerField();

        if (status.equals("Success")) {

            String fname = jTextField10.getText();
            String lname = jTextField11.getText();
            String email = jTextField12.getText();
            String mobile = jTextField13.getText();
            boolean isFound = false;

            for (String vectorEmail : customerVector) {
                Customer customer = customerMap.get(vectorEmail);
                if (!customer.getEmail().equals(email) && customer.getMobile().equals(mobile)) {
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                MYSQL.Iud("UPDATE `customer` SET `firstName`='" + fname + "', `lastName`='" + lname + "', `mobile`='" + mobile + "' WHERE `email`='" + email + "' ");
                resetCustomerFields();
                loadCustomers();
                loadCustomerTable();

            } else {
                Alert("This Customer Mobile Already Exist ....");
            }

        } else {
            Alert(status);
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jTextField14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField14FocusGained
        jTextField14.setText("");
        jTextField14.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField14FocusGained

    private void jTextField14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField14FocusLost
        if (jTextField14.getText().isEmpty()) {
            jTextField14.setText("Search By Mobile Number .... ");
            jTextField14.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_jTextField14FocusLost

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        String order = null;
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);

        if (jComboBox6.getSelectedIndex() == 0) {
            order = "ASC";
        } else {
            order = "DESC";
        }

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `customer` WHERE `email` LIKE '%" + jTextField14.getText() + "%' ORDER BY `firstName` " + order + " ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("firstName"));
                vector.add(resultSet.getString("lastName"));
                vector.add(resultSet.getString("mobile"));
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        String order = null;
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);

        if (jComboBox6.getSelectedIndex() == 0) {
            order = "ASC";
        } else {
            order = "DESC";
        }

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `customer` ORDER BY `firstName` " + order + " ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("firstName"));
                vector.add(resultSet.getString("lastName"));
                vector.add(resultSet.getString("mobile"));
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        resetBrandFields();
        loadBrandTable();

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        boolean isFound = false;

        if (jTextField17.getText().isEmpty()) {
            Alert("Enter Brand Name ....");

        } else {
            for (String vectorBrand : brandVector) {
                if (vectorBrand.equals(jTextField17.getText())) {
                    isFound = true;
                    break;

                }
            }

            if (!isFound) {
                MYSQL.Iud("INSERT INTO `brand`(`name`) VALUES ('" + jTextField17.getText() + "') ");
                loadBrand();
                loadBrandTable();
                loadBrandComboBox();
                resetBrandFields();

            } else {
                Alert("This Brand Already Exist ....");
            }

        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        if (evt.getClickCount() == 2) {
            int selectedRow = jTable3.getSelectedRow();

            if (selectedRow != -1) {
                jTextField6.setText(String.valueOf(jTable3.getValueAt(selectedRow, 0)));
                jTextField17.setText(String.valueOf(jTable3.getValueAt(selectedRow, 1)));

                jTable3.setEnabled(false);
                jButton7.setEnabled(false);

                jLabel26.setVisible(true);
                jTextField6.setVisible(true);
                jButton11.setEnabled(true);
            }

        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        boolean isFound = false;

        if (jTextField17.getText().isEmpty()) {
            Alert("Enter Brand Name ....");

        } else {
            for (String vectorBrand : brandVector) {
                if (vectorBrand.equals(jTextField17.getText())) {
                    isFound = true;
                    break;

                }
            }

            if (!isFound) {
                MYSQL.Iud("UPDATE  `brand` SET `name`='" + jTextField17.getText() + "' WHERE `id`='" + jTextField6.getText() + "' ");
                loadBrand();
                loadBrandTable();
                loadBrandComboBox();
                resetBrandFields();

            } else {
                Alert("This Brand Already Exist ....");
            }

        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTextField9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField9KeyReleased
        try {

            ResultSet resultSet = MYSQL.Search("SELECT * FROM `brand` WHERE `name` LIKE '%" + jTextField9.getText() + "%' ");

            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("name"));
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTextField9KeyReleased

    private void jTextField9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusGained
        jTextField9.setText("");
        jTextField9.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField9FocusGained

    private void jTextField9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusLost
        if (jTextField9.getText().isEmpty()) {
            jTextField9.setText("Search Brand By Name ....");
            jTextField9.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_jTextField9FocusLost

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        String status = validateProductFields();
        boolean isFound = false;

        if (status.equals("Success")) {
            String pname = jTextField4.getText();
            String pbrand = String.valueOf(jComboBox7.getSelectedItem());

            int brandId = brandMap.get(pbrand);

            for (String vectorId : productVector) {
                Product product = productMap.get(vectorId);
                if (product.getName().equalsIgnoreCase(pname) && product.getBrandID().equals(String.valueOf(brandId))) {
                    isFound = true;

                }

            }

            if (!isFound) {
                MYSQL.Iud("INSERT INTO `product` (`brand_id`,`name`) VALUES ('" + brandId + "','" + pname + "') ");
                loadProduct();
                loadProductTable();
                resetProductFields();

            } else {
                Alert("This Product Already Exist .....");
            }

        } else {
            Alert(status);
        }


    }//GEN-LAST:event_jButton14ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {

            int row = jTable2.getSelectedRow();

            if (row != -1) {
                jTextField18.setText(String.valueOf(jTable2.getValueAt(row, 0)));
                jTextField4.setText(String.valueOf(jTable2.getValueAt(row, 1)));
                jComboBox7.setSelectedItem(String.valueOf(jTable2.getValueAt(row, 2)));

                jTable2.setEnabled(false);
                jComboBox7.setEnabled(false);
                jTextField8.setEnabled(false);
                jComboBox4.setEnabled(false);
                jButton14.setEnabled(false);
                jButton13.setEnabled(true);
                jLabel28.setVisible(true);
                jTextField18.setVisible(true);

            }

        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        resetProductFields();
        loadProductTable();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        String status = validateProductFields();
        boolean isFound = false;

        if (status.equals("Success")) {
            String pid = jTextField18.getText();
            String pname = jTextField4.getText();
            String pbrand = String.valueOf(jComboBox7.getSelectedItem());

            int brandId = brandMap.get(pbrand);

            for (String vectorId : productVector) {
                Product product = productMap.get(vectorId);
                if (product.getName().equalsIgnoreCase(pname) && product.getBrandID().equals(String.valueOf(brandId)) && !product.getPid().equals(pid)) {
                    isFound = true;

                }

            }

            if (!isFound) {
                MYSQL.Iud("UPDATE `product` SET `name`='" + pname + "' WHERE `id`='" + pid + "' ");
                loadProduct();
                loadProductTable();
                resetProductFields();

            } else {
                Alert("This Product Already Exist .....");
            }

        } else {
            Alert(status);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTextField8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyReleased
        String order = null;
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        if (jComboBox4.getSelectedIndex() == 0) {
            order = "ASC";
        } else {
            order = "DESC";
        }

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `product` INNER JOIN `brand` ON `brand`.`id`=`product`.`brand_id` WHERE `product`.`name` LIKE '%" + jTextField8.getText() + "%' ORDER BY `product`.`name` " + order + " ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("product.name"));
                vector.add(resultSet.getString("brand.name"));
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTextField8KeyReleased

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        String order = null;
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        if (jComboBox4.getSelectedIndex() == 0) {
            order = "ASC";
        } else {
            order = "DESC";
        }

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `product` INNER JOIN `brand` ON `brand`.`id`=`product`.`brand_id` ORDER BY `product`.`name` " + order + " ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("product.name"));
                vector.add(resultSet.getString("brand.name"));
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        ManageEmployeePanel.setVisible(false);
        ManageGRNPanel.setVisible(false);
        ManageProductPanel.setVisible(false);
        ManageCustomerPanel.setVisible(false);
        ManageStockPanel.setVisible(false);
        ManageInvoicePanel.setVisible(false);
        ManageSupplierPanel.setVisible(true);
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (jTextField19.getText().isEmpty()) {
            Alert("Enter Company name");

        } else {

            String companyName = jTextField19.getText();
            boolean isFound = false;

            for (String vectorName : companyVector) {
                if (vectorName.equals(companyName)) {
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                MYSQL.Iud("INSERT INTO `company`(`company_name`) VALUES ('" + companyName + "') ");
                loadCompanies();
                loadCopaniesTable();
                resetCompanyFields();

            } else {
                Alert("This Company Already Exist ....");
            }

        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        if (evt.getClickCount() == 2) {
            int row = jTable5.getSelectedRow();

            if (row != -1) {
                jTextField21.setText(String.valueOf(jTable5.getValueAt(row, 0)));
                jTextField19.setText(String.valueOf(jTable5.getValueAt(row, 1)));
                jButton19.setEnabled(true);
                jTextField20.setEnabled(false);
                jTable5.setEnabled(false);
                jButton10.setEnabled(false);
                jLabel31.setVisible(true);
                jTextField21.setVisible(true);
            }
        }
    }//GEN-LAST:event_jTable5MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        resetCompanyFields();
        loadCopaniesTable();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        if (jTextField19.getText().isEmpty()) {
            Alert("Enter Company name");

        } else {

            String companyId = jTextField21.getText();
            String companyName = jTextField19.getText();
            boolean isFound = false;

            for (String vectorName : companyVector) {
                String comId = companyMap.get(vectorName);
                if (vectorName.equals(companyName) && !comId.equals(companyId)) {
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                MYSQL.Iud("UPDATE `company` SET `company_name`='" + companyName + "' WHERE `id`='" + companyId + "' ");
                loadCompanies();
                loadCopaniesTable();
                resetCompanyFields();

            } else {
                Alert("This Company Already Exist ....");
            }

        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jTextField20FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField20FocusGained
        jTextField20.setText("");
        jTextField20.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField20FocusGained

    private void jTextField20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField20FocusLost
        if (jTextField20.getText().isEmpty()) {
            jTextField20.setText("Search By Name ....");
            jTextField20.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_jTextField20FocusLost

    private void jTextField20KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField20KeyReleased
        DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
        model.setRowCount(0);

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `company` WHERE `company_name` LIKE '%" + jTextField20.getText() + "%' ");
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("company_name"));
                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }//GEN-LAST:event_jTextField20KeyReleased

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        String status = validateSupplierFields();

        if (status.equals("Success")) {

            String fname = jTextField22.getText();
            String lname = jTextField23.getText();
            String email = jTextField25.getText();
            String mobile = jTextField24.getText();
            boolean isFound = false;

            for (String vectorEmail : supplierVector) {
                Supplier supplier = supplierMap.get(vectorEmail);
                if (vectorEmail.equals(email) && supplier.getMobile().equals(mobile)) {
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                MYSQL.Iud("INSERT INTO `supplier`(`email`,`firstName`,`lastName`,`mobile`) VALUES ('" + email + "','" + fname + "','" + lname + "','" + mobile + "')");
                resetSupplierFields();
                loadSuppliers();
                loadSuppliersTable();

            } else {
                Alert("This Supplier Already Exist ....");

            }

        } else {
            Alert(status);
        }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        if (evt.getClickCount() == 2) {
            int row = jTable6.getSelectedRow();

            if (row != -1) {
                jTextField25.setText(String.valueOf(jTable6.getValueAt(row, 0)));
                jTextField22.setText(String.valueOf(jTable6.getValueAt(row, 1)));
                jTextField23.setText(String.valueOf(jTable6.getValueAt(row, 2)));
                jTextField24.setText(String.valueOf(jTable6.getValueAt(row, 3)));

                jTable6.setEnabled(false);
                jTextField25.setEnabled(false);
                jComboBox3.setEnabled(false);
                jTextField26.setEnabled(false);
                jButton22.setEnabled(false);
                jButton21.setEnabled(true);

            }

        }
    }//GEN-LAST:event_jTable6MouseClicked

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        resetSupplierFields();
        loadSuppliersTable();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        String status = validateSupplierFields();

        if (status.equals("Success")) {

            String fname = jTextField22.getText();
            String lname = jTextField23.getText();
            String email = jTextField25.getText();
            String mobile = jTextField24.getText();
            boolean isFound = false;

            for (String vectorEmail : supplierVector) {
                Supplier supplier = supplierMap.get(vectorEmail);
                if (!vectorEmail.equals(email) && supplier.getMobile().equals(mobile)) {
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                MYSQL.Iud("UPDATE `supplier` SET `firstName`='" + fname + "', `lastName`='" + lname + "', `mobile`='" + mobile + "' WHERE `email`='" + email + "' ");
                resetSupplierFields();
                loadSuppliers();
                loadSuppliersTable();

            } else {
                Alert("This Mobile Number Already Exist ....");

            }

        } else {
            Alert(status);
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jTextField26FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField26FocusGained
        jTextField26.setText("");
        jTextField26.setForeground(Color.BLACK);

    }//GEN-LAST:event_jTextField26FocusGained

    private void jTextField26FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField26FocusLost
        if (jTextField26.getText().isEmpty()) {
            jTextField26.setText("Search By Email Address .....");
            jTextField26.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_jTextField26FocusLost

    private void jTextField26KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField26KeyReleased
        String order = null;
        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.setRowCount(0);

        if (jComboBox3.getSelectedIndex() == 0) {
            order = "ASC";
        } else {
            order = "DESC";
        }

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `supplier` WHERE `email` LIKE '%" + jTextField26.getText() + "%' ORDER BY `firstName` " + order + " ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("firstName"));
                vector.add(resultSet.getString("lastName"));
                vector.add(resultSet.getString("mobile"));
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTextField26KeyReleased

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        String order = null;
        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.setRowCount(0);

        if (jComboBox3.getSelectedIndex() == 0) {
            order = "ASC";
        } else {
            order = "DESC";
        }

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `supplier` ORDER BY `firstName` " + order + " ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("firstName"));
                vector.add(resultSet.getString("lastName"));
                vector.add(resultSet.getString("mobile"));
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jTextField34FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField34FocusGained
        jTextField34.setText("");
        jTextField34.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField34FocusGained

    private void jTextField34FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField34FocusLost
        if (jTextField34.getText().isEmpty()) {
            jTextField34.setText("Search By Product Name ....");
            jTextField34.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_jTextField34FocusLost

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        ManageEmployeePanel.setVisible(false);
        ManageGRNPanel.setVisible(false);
        ManageProductPanel.setVisible(false);
        ManageCustomerPanel.setVisible(false);
        ManageSupplierPanel.setVisible(false);
        ManageInvoicePanel.setVisible(false);
        ManageStockPanel.setVisible(true);
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked
        if (evt.getClickCount() == 2) {

            int row = jTable7.getSelectedRow();

            if (row != -1) {
                jTextField39.setText(String.valueOf(jTable7.getValueAt(row, 0)));
                jTextField27.setText(String.valueOf(jTable7.getValueAt(row, 1)));
                jTextField28.setText(String.valueOf(jTable7.getValueAt(row, 2)));
                jTextField29.setText(String.valueOf(jTable7.getValueAt(row, 3)));
                jTextField37.setText(String.valueOf(jTable7.getValueAt(row, 4)));
                jComboBox13.setSelectedItem(String.valueOf(jTable7.getValueAt(row, 5)));
                jTextField38.setText(String.valueOf(jTable7.getValueAt(row, 6)));

                jTable7.setEnabled(false);

                jButton32.setEnabled(false);
                jButton33.setEnabled(false);
                jButton31.setEnabled(true);

            }
        }
    }//GEN-LAST:event_jTable7MouseClicked

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        StockProductTable grnProduct = new StockProductTable(this, true);
        grnProduct.setVisible(true);
        jTextField27.setText(pid);
        jTextField28.setText(pname);
        jTextField29.setText(pbname);
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        String status = validateStockManageField();

        if (status.equals("Success")) {
            String stockID = jTextField39.getText();
            String quentity = jTextField38.getText();
            String sellPrice = jTextField37.getText();
            String warrentyID = stockWarrenttyID.get(String.valueOf(jComboBox13.getSelectedItem()));
            String productID = jTextField27.getText();

            MYSQL.Iud("INSERT INTO `stock`(`id`,`product_id`,`price`,`qty`,`warrenty_id`) VALUES('" + stockID + "','" + productID + "','" + sellPrice + "','" + quentity + "','" + warrentyID + "') ");
            loadGRNStockTable();
            resetStockFields();

        } else {
            Alert(status);
        }

    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        resetStockFields();
        loadGRNStockTable();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        String status = validateStockManageField();

        if (status.equals("Success")) {
            String stockID = jTextField39.getText();
            String quentity = jTextField38.getText();
            String sellPrice = jTextField37.getText();
            String warrentyID = stockWarrenttyID.get(String.valueOf(jComboBox13.getSelectedItem()));

            MYSQL.Iud("UPDATE `stock` SET `price`='" + sellPrice + "', `qty`='" + quentity + "', `warrenty_id`='" + warrentyID + "' WHERE `id`='" + stockID + "' ");
            loadGRNStockTable();
            resetStockFields();

        } else {
            Alert(status);
        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jTextField34KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField34KeyReleased
        DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
        model.setRowCount(0);

        try {
            ResultSet resultSet = MYSQL.Search("SELECT * FROM `stock` INNER JOIN `product` ON `product`.`id`=`stock`.`product_id` INNER JOIN `brand` ON `brand`.`id`=`product`.`brand_id` INNER JOIN `warrenty` ON `warrenty`.`id`=`stock`.`warrenty_id` WHERE `product`.`name` LIKE '%" + jTextField34.getText() + "%' OR `stock`.`id` LIKE '" + jTextField34.getText() + "%' ");

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("stock.id"));
                vector.add(resultSet.getString("product.id"));
                vector.add(resultSet.getString("product.name"));
                vector.add(resultSet.getString("brand.name"));
                vector.add(resultSet.getString("stock.price"));
                vector.add(resultSet.getString("duration"));
                vector.add(resultSet.getString("stock.qty"));
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }//GEN-LAST:event_jTextField34KeyReleased

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        ManageEmployeePanel.setVisible(false);
        ManageProductPanel.setVisible(false);
        ManageCustomerPanel.setVisible(false);
        ManageSupplierPanel.setVisible(false);
        ManageStockPanel.setVisible(false);
        ManageInvoicePanel.setVisible(false);
        ManageGRNPanel.setVisible(true);
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        jTextField33.setEnabled(false);
        jTextField36.setEnabled(false);

        String status = validateGRNFields();

        if (status.equals("Success")) {
            String stockID = jTextField32.getText();
            String name = jTextField35.getText();
            String brand = jTextField41.getText();
            String warrenty = jTextField42.getText();
            String qty = jTextField40.getText();
            String buyPrice = jTextField33.getText();
            String sellPrice = jTextField36.getText();
            String addQty = jTextField43.getText();
            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
            totalProducts = 0;
            totalQty = 0;
            totalPrice = 0;

            boolean isFound = false;

            if (jTable8.getRowCount() == 0 && !firstRowIsAdded) {
                Vector<String> vector = new Vector<>();
                vector.add(stockID);
                vector.add(name);
                vector.add(brand);
                vector.add(warrenty);
                vector.add(qty);
                vector.add(buyPrice);
                vector.add(sellPrice);
                vector.add(addQty);
                model.addRow(vector);

                firstRowIsAdded = true;

                CalculateGrnTable();

            } else {
                for (int i = 0; i < jTable8.getRowCount(); i++) {
                    String sID = String.valueOf(jTable8.getValueAt(i, 0));

                    if (stockID.equals(sID)) {
                        isFound = true;
                    }
                }

                if (!isFound) {
                    Vector<String> vector = new Vector<>();
                    vector.add(stockID);
                    vector.add(name);
                    vector.add(brand);
                    vector.add(warrenty);
                    vector.add(qty);
                    vector.add(buyPrice);
                    vector.add(sellPrice);
                    vector.add(addQty);
                    model.addRow(vector);
                } else {
                    Alert("This Stock Already Exist .... ");
                }
                CalculateGrnTable();
            }
//         
            jTextField30.setText(String.valueOf(jTable8.getRowCount()));
            jTextField46.setText(String.valueOf(totalQty));
            jLabel63.setText(String.valueOf(totalPrice));

            resetGrnStockFields();
//            
        } else {
            Alert(status);
        }

    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        GRNSupplierTable grnSupplierTable = new GRNSupplierTable(this, true);
        grnSupplierTable.setVisible(true);
        jTextField31.setText(GrnsuppilerEmail);
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        GRNStockTable grnStockTable = new GRNStockTable(this, true);
        grnStockTable.setVisible(true);

        jTextField32.setText(GrnStockID);
        jTextField35.setText(GrnName);
        jTextField41.setText(GrnBrand);
        jTextField42.setText(GrnWarrenty);
        jTextField33.setText(GrnBuyPrice);
        jTextField36.setText(GrnSellPrice);
        jTextField40.setText(GrnQty);

        if (newStock) {
            jTextField33.setEnabled(true);
            jTextField36.setEnabled(true);
        } else {
            jTextField33.setEnabled(false);
            jTextField36.setEnabled(false);
        }


    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        dateChooser3.showPopup();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        GRNCompanyTable grnCompanyTable = new GRNCompanyTable(this, true);
        grnCompanyTable.setVisible(true);
        jTextField45.setText(GrnCompanyId);
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        resetGrnStockFields();
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        resetGrnStockFields();
        resetGrnAllFields();
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jTable8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable8MouseClicked
        if (evt.getClickCount() == 1) {

            int row = jTable8.getSelectedRow();

            if (row != -1) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    jPopupMenu2.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }

        }
    }//GEN-LAST:event_jTable8MouseClicked

    private void UpdateRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateRowActionPerformed
        int row = jTable8.getSelectedRow();

        jTextField32.setText(String.valueOf(jTable8.getValueAt(row, 0)));
        jTextField35.setText(String.valueOf(jTable8.getValueAt(row, 1)));
        jTextField41.setText(String.valueOf(jTable8.getValueAt(row, 2)));
        jTextField42.setText(String.valueOf(jTable8.getValueAt(row, 3)));
        jTextField40.setText(String.valueOf(jTable8.getValueAt(row, 4)));

        String bPrice = String.valueOf(jTable8.getValueAt(row, 5));
        if (bPrice.startsWith("0")) {
            jTextField33.setEnabled(true);
        } else {
            jTextField33.setEnabled(false);
        }

        jTextField33.setText(bPrice);
        jTextField36.setText(String.valueOf(jTable8.getValueAt(row, 6)));
        jTextField43.setText(String.valueOf(jTable8.getValueAt(row, 7)));

        jButton27.setVisible(false);
        jButton35.setVisible(true);


    }//GEN-LAST:event_UpdateRowActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        String stockID = jTextField32.getText();
        String name = jTextField35.getText();
        String brand = jTextField41.getText();
        String warrenty = jTextField42.getText();
        String qty = jTextField40.getText();
        String buyPrice = jTextField33.getText();
        String sellPrice = jTextField36.getText();
        String addQty = jTextField43.getText();

        for (int i = 0; i < jTable8.getRowCount(); i++) {
            String sID = String.valueOf(jTable8.getValueAt(i, 0));

            if (stockID.equals(sID)) {

                jTable8.setValueAt(stockID, i, 0);
                jTable8.setValueAt(name, i, 1);
                jTable8.setValueAt(brand, i, 2);
                jTable8.setValueAt(warrenty, i, 3);
                jTable8.setValueAt(qty, i, 4);
                jTable8.setValueAt(buyPrice, i, 5);
                jTable8.setValueAt(sellPrice, i, 6);
                jTable8.setValueAt(addQty, i, 7);

                break;
            }
        }

        CalculateGrnTable();
        resetGrnStockFields();

        jButton35.setVisible(false);
        jButton27.setVisible(true);

    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        String employeeEmail = "Sanuth@gmail.com";
        String supplierEmail = jTextField31.getText();
        String companyId = jTextField45.getText();
        String date = jTextField44.getText();
        String tPrice = jLabel63.getText();
        String uniqId = generateUniqueId();
        MYSQL.Iud("INSERT INTO `grn`(`id`,`employee_email`,`price`,`purchesedDate`,`supplier_email`,`company_id`) VALUES('" + uniqId + "','" + employeeEmail + "','" + tPrice + "','" + date + "','" + supplierEmail + "','" + companyId + "') ");

        for (int i = 0; i < jTable8.getRowCount(); i++) {
            String stockID = String.valueOf(jTable8.getValueAt(i, 0));
            String existqty = String.valueOf(jTable8.getValueAt(i, 4));
            String buyPrice = String.valueOf(jTable8.getValueAt(i, 5));
            String sellPrice = String.valueOf(jTable8.getValueAt(i, 6));
            String newQty = String.valueOf(jTable8.getValueAt(i, 7));

            int quentity = Integer.parseInt(existqty) + Integer.parseInt(newQty);

            MYSQL.Iud("UPDATE `stock` SET `price`='" + sellPrice + "',`qty`='" + quentity + "' WHERE `id`='" + stockID + "' ");
            MYSQL.Iud("INSERT INTO `grn_item`(`buying_price`,`qty`,`stock_id`,`grn_id`)VALUES('" + buyPrice + "','" + newQty + "','" + stockID + "','" + uniqId + "')");

        }

        resetGrnStockFields();
        resetGrnAllFields();

    }//GEN-LAST:event_jButton34ActionPerformed

    private void DeleteRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteRowActionPerformed
        int row = jTable8.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
        model.removeRow(row);

        CalculateGrnTable();

    }//GEN-LAST:event_DeleteRowActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        String status = validateInvoiceFields();
        boolean isFound = false;
        DefaultTableModel model = (DefaultTableModel) jTable9.getModel();

        if (status.equals("Success")) {
            invoiceTotalProducts = 0;
            invoiceTotalQty = 0;
            invoiceTotalPrice = 0;

            if (jTable9.getRowCount() == 0 && !invoiceFirstRowIsAdded) {
                Vector<String> vector = new Vector<>();
                vector.add(jTextField49.getText());
                vector.add(jTextField50.getText());
                vector.add(jTextField51.getText());
                vector.add(jTextField52.getText());
                vector.add(jTextField53.getText());
                model.addRow(vector);
                invoiceFirstRowIsAdded = true;

                calculateInvoiceTable();

            } else {
                String StockID = null;
                for (int i = 0; i < jTable9.getRowCount(); i++) {
                    StockID = String.valueOf(jTable9.getValueAt(i, 0));

                    if (StockID.equals(jTextField49.getText())) {
                        isFound = true;
                    }
                }
                if (!isFound) {
                    Vector<String> vector = new Vector<>();
                    vector.add(jTextField49.getText());
                    vector.add(jTextField50.getText());
                    vector.add(jTextField51.getText());
                    vector.add(jTextField52.getText());
                    vector.add(jTextField53.getText());
                    model.addRow(vector);

                    calculateInvoiceTable();

                } else {
                    Alert("This Produuct All Ready Exist .... ");
                }
            }

            jTextField55.setText(String.valueOf(invoiceTotalProducts));
            jTextField56.setText(String.valueOf(invoiceTotalQty));
            jLabel74.setText(String.valueOf(invoiceTotalPrice));

            resetInvoiceAddProductFields();

        } else {
            Alert(status);
        }

    }//GEN-LAST:event_jButton39ActionPerformed

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        ManageEmployeePanel.setVisible(false);
        ManageProductPanel.setVisible(false);
        ManageCustomerPanel.setVisible(false);
        ManageSupplierPanel.setVisible(false);
        ManageStockPanel.setVisible(false);
        ManageGRNPanel.setVisible(false);
        ManageInvoicePanel.setVisible(true);
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        InvoiceCustomerTable invoiceCustomerTable = new InvoiceCustomerTable(this, true);
        invoiceCustomerTable.setVisible(true);
        jTextField48.setText(InvoiceCustomerEmail);
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        InvoiceStockTable invoiceStockTable = new InvoiceStockTable(this, true);
        invoiceStockTable.setVisible(true);
        jTextField49.setText(InvoiceStockID);
        jTextField50.setText(InvoiceProductName);
        jTextField51.setText(InvoiceBrandName);
        jTextField52.setText(InvoiceUnitePrice);

    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        resetInvoiceAddProductFields();
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        resetInvoiceAddProductFields();
        resetAllInvoiceFields();
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jTextField54KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField54KeyReleased
        if (jTextField54.getText().equals("0") || jTextField54.getText().isEmpty()) {
            jLabel79.setText(String.valueOf(invoiceTotalPrice));

        } else {

            double percentage = Double.parseDouble(jTextField54.getText());
            double total = Double.parseDouble(String.valueOf(invoiceTotalPrice));

            double GrandTotal = (percentage / 100) * total;
            jLabel79.setText(String.valueOf((total + GrandTotal)));
        }
    }//GEN-LAST:event_jTextField54KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
//        FlatDarkLaf.setup();
        FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem DeleteRow;
    private javax.swing.JPanel ManageCustomerPanel;
    private javax.swing.JPanel ManageEmployeePanel;
    private javax.swing.JPanel ManageGRNPanel;
    private javax.swing.JPanel ManageInvoicePanel;
    private javax.swing.JPanel ManageProductPanel;
    private javax.swing.JPanel ManageStockPanel;
    private javax.swing.JPanel ManageSupplierPanel;
    private javax.swing.JMenuItem UpdateRow;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private com.raven.datechooser.DateChooser dateChooser3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonM7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
