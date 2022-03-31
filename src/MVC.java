import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Locale.Category;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import javax.swing.text.NumberFormatter;

import java.util.logging.Level;

public class MVC {
    public static final Logger logger = Logger.getLogger("gestio_logger");
    public static Scanner scan = new Scanner(System.in);

    public static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
    public static Locale localitzacioFormat = Locale.getDefault(Category.FORMAT);
    
    public static ResourceBundle texts = ResourceBundle.getBundle("Texts", localitzacioFormat);
    
    public static void main(String[] args) throws IOException{
        try {
            FileHandler fileHandler = new FileHandler("./logs/aplication_log.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.info("Logger Set UP");
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

        //Fallo controlado para el ejercicio
        int[] newArray = new int[2];

        try {
            for (int i = 0; i < 3; i++) {
                System.out.println(newArray[i]);
            }
        } catch (RuntimeException e) {
            logger.warning(e.getMessage());
        }            

        ProductsDAO productsDAO = new ProductsDAO();
        CustomersDAO customersDAO = new CustomersDAO();
        ProvidersDAO providersDAO = new ProvidersDAO();
        PresenceDAO PresenceDAO = new PresenceDAO();

        productsDAO.load();
        // customersDAO.load();
        // providersDAO.load();

        menu(productsDAO, customersDAO, providersDAO, PresenceDAO);
    }

    public static void menu(ProductsDAO products, CustomersDAO customers, ProvidersDAO providers, PresenceDAO presence) throws IOException{
        int option = 100;

        do {
            System.out.println("\n"+texts.getString("menu.title"));

            System.out.println("\n"+ texts.getString("menu.selectOpc") +"\n");
            System.out.println("1. "+texts.getString("menu.products"));
            System.out.println("2. "+texts.getString("menu.customers"));
            System.out.println("3. "+texts.getString("menu.providers"));
            System.out.println("4. "+texts.getString("menu.presence"));

            System.out.println("\n0. "+texts.getString("menu.exit"));

            try {
                option = scan.nextInt();
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
           
            switch (option) {
                case 1:
                    productsMenu(products);
                    break;
                case 2:
                    customersMenu(customers);
                    break;
                case 3:
                    providersMenu(providers);
                    break;
                case 4:
                    presenceMenu(presence);
                default:
                    break;
            }
        } while(option != 0);
        products.save();
    }

    //Menu de presencia
    public static void presenceMenu(PresenceDAO presence){
        int option = 100;

        do{
            System.out.println("\nPresencia\n");
            System.out.println("1. Entrada");
            System.out.println("2. Salida");
            System.out.println("3. Consultar");
            System.out.println("0. Salir");
            System.out.println("Opcion: ");

            option = scan.nextInt();

            switch (option) {
                case 1:
                    //Entrada
                    presenceEntry(presence);
                    break;
                case 2:
                    //Salida
                    presenceOut(presence);
                    break;
                case 3:
                    //Consulta
                    checkPresence(presence);
                    break;
                default:
                    System.out.println("Opcion incorrecta :(");
                    break;
            }
        }while(option!=0);
    }

    public static void presenceEntry(PresenceDAO presence){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Introduce tu ID: ");
        int id = scan.nextInt();
        System.out.println("Introduce la fecha(dd/MM/yyyy): ");
        String dateString = scan.next();
        LocalDate date = LocalDate.parse(dateString, dtf);
        LocalTime time = LocalTime.now();

        Presence p = new Presence(id, date, time);
        if(presence.add(p)!=null){
            System.out.println("Presencia añadida.");
        } else {
            System.out.println("Presencia no añadida.");
        }
    }

    public static void presenceOut(PresenceDAO presence){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Introduce tu ID: ");
        int id = scan.nextInt();
        System.out.println("Introuce la fecha: ");
        String dateString = scan.nextLine();
        LocalDate date = LocalDate.parse(dateString, dtf);
        Presence temp = presence.search(id, date);
        if(temp!=null){
            temp.setDeparture(LocalTime.now());
        }
    }

    public static void checkPresence(PresenceDAO presence){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Comprobar Presencia");
        System.out.println("1. Dia");
        System.out.println("2. Trabajador");

        int opc = scan.nextInt();
        if(opc == 1){
            System.out.println("Introduce un dia: ");
            String dateString = scan.next();
            LocalDate date= LocalDate.parse(dateString, dtf);
            ArrayList<Presence> dayPresenceList = presence.searchDay(date);
            for(int i = 0; i<dayPresenceList.size(); i++){
                System.out.println(dayPresenceList.get(i));
            }
        } else if (opc == 2) {
            System.out.println("Introduce un ID");
            int id = scan.nextInt();
            ArrayList<Presence> idPresenceList = presence.searchId(id);
            for(int i = 0; i<idPresenceList.size(); i++){
                System.out.println(idPresenceList.get(i));
            }
        } else {
            System.out.println("Opcion Incorrecta");
        }
    }

    //Menu Productos
    public static void productsMenu(ProductsDAO products) throws IOException{
        int option = 100;

        do {
            System.out.println("\nProductos\n");
            System.out.println("1. Agregar Producto.");
            System.out.println("2. Buscar Producto.");
            System.out.println("3. Modificar Producto.");
            System.out.println("4. Borrar Producto.");
            System.out.println("5. Mostrar todos los productos.");
            System.out.println("6. Introducir Stock a un producto.");
            System.out.println("7. Quitar Stock a un producto.");
            System.out.println("8. Crear nuevo pedido.");
            System.out.println("9. Imprimir Productos Ordenados.");
            System.out.println("10. Productos Descatalogados.");
            System.out.println("0. Salir");
            System.out.println("Opcion: \n");

            try {
                option = scan.nextInt();
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

            switch (option) {
                case 1:
                    char o = ' ';
                    do {
                        System.out.println("\nQue quieres Introducir?");
                        System.out.println("1. Producto");
                        System.out.println("2. Pack");
                        System.out.println("0. Atras\n");

                        try {
                            o = scan.next().charAt(0);
                        } catch (Exception e) {
                            logger.warning(e.getMessage());
                        }

                        if(o=='1'){
                            addProduct(products);
                        } else if(o=='2') {
                            addPack(products);
                        } else {
                            System.out.println("Opcion Invalida");
                        }
                    } while (o!='0');
                
                    break;
                case 2:
                    searchProduct(products);
                    break;
                case 3:
                    modifyProduct(products);
                    break;
                case 4:
                    deleteProduct(products);
                    break;
                case 5:
                    showProducts(products);
                    break;
                case 6:
                    //Introducir Stock a un producto
                    System.out.println("\nIntroducir Stock");
                    System.out.println("\n1. Introducir de Manera Manual.");
                    System.out.println("2. Introducir de manera Automatica.");
                    int inputStockOption = scan.nextInt();

                    switch (inputStockOption) {
                        case 1:
                            System.out.println("A que producto quieres introducir Stock? (ID) ");
                            int putStockId = scan.nextInt();
                            System.out.println("Ahora introduce el Stock que quieres añadir: ");
                            int newStock = scan.nextInt();
                            products.search(putStockId).putStock(newStock);
                            break;
                        case 2:
                            automaticStockInput(products);
                            break;
                        default:
                            break;
                    }

                    break;
                case 7:
                    System.out.println("A que producto quieres quitarle Stock? (ID)");
                    int takeStockId = scan.nextInt();
                    System.out.println("Ahora introduce la cantidad que quieres retirar: ");
                    int takeStock = scan.nextInt();
                    try {
                        products.search(takeStockId).takeStock(takeStock);
                    } catch (StockInsuficientException e) {
                        logger.warning(e.getMessage());
                    }

                    break;
                case 8:
                    createNewOrder();
                    break;
                case 9:
                    showOrderedProducts(products);
                    break;
                case 10:
                    discontinuedProducts(products);
                    break;
                default:
                    System.out.println("Opcion Incorrecta");
                    break;
            }

        } while (option!=0);
    }

    //Consulta que productos estan descatalogados.
    public static void discontinuedProducts(ProductsDAO products){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Introduce una fecha para comparar(dd/MM/yyyy): ");
        String sDate = scan.nextLine();
        LocalDate date = null;
        if(sDate==""){
            date = LocalDate.now();
        } else {
            date = LocalDate.parse(sDate, dtf);
        }
        
        ArrayList<String> discontinuedProducts = products.getDiscontinuedProducts(date);

        System.out.println("Productos Descatalogados:");
        for(int i=0;i<discontinuedProducts.size();i++){
            System.out.println(discontinuedProducts.get(i));
        }
    }

    //Muestra los productos de forma ordenada
    public static void showOrderedProducts(ProductsDAO products){
        ArrayList<Product> listOfProducts = new ArrayList<Product>();
        HashMap map = products.getMap();

        for(Object value : map.values()){
            listOfProducts.add((Product)value);
        }

        System.out.println("Por que campo quieres ordenar?");
        System.out.println("1. Nombre");
        System.out.println("2. Precio");
        System.out.println("3. Stock");
        int orderOption = scan.nextInt();
        Comparator<Product> productOrder = null;

        if(orderOption == 1){
            productOrder = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getName().compareTo(o2.getName());
                } 
            };
        } else if(orderOption == 2){
            productOrder = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                } 
            };
        } else if(orderOption==3){
            productOrder = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getStock().compareTo(o2.getStock());
                } 
            };
        }

        System.out.println("\nLista Desordenada\n");
        System.out.println(listOfProducts);
        Collections.sort(listOfProducts, productOrder);
        System.out.println("\nLista Ordenada\n");
        System.out.println(listOfProducts);
    }

    //Crea un nuevo pedido
    public static void createNewOrder() throws IOException{
        System.out.println("\nCrear nuevo Pedido");
        char moreOrders = ' ';
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("tmp.txt"));
        do{
            System.out.println("Introduce el Id del producto que quieres guardar.");
            String id = scan.next();
            System.out.println("Introduce la cantidad del producto");
            int stock = scan.nextInt();

            dos.writeUTF(id);
            dos.writeInt(stock);

            System.out.println("Quieres introducir mas productos?[y/n]");
            moreOrders = scan.next().charAt(0);
        }while(moreOrders!='n');
        dos.close();

        System.out.println("Introduce el nombre del fichero donde guardar la orden: ");
        String fileName = scan.next();
        Path mainFile = Paths.get("tmp.txt");
        System.out.println(mainFile);
        Files.move(mainFile, mainFile.resolveSibling(fileName+".txt"));
        System.out.println("Tu pedido ha sido guardado como: "+fileName+".txt");
    }

    //Introduce stock al sistema a traves de un fichero
    private static void automaticStockInput(ProductsDAO products) throws IOException {
        System.out.println("Introduce el nombre de la comanda: ");
        String orderName = scan.next();
        orderName = hasExtensionTXT(orderName);
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(orderName)));
        while(dis.available()>0){
            String idProductString = dis.readUTF();
            int productStock = dis.readInt();
            int idProductInt = Integer.parseInt(idProductString);

            if(products.search(idProductInt)!= null){
                products.search(idProductInt).putStock(productStock);
                System.out.println("Se ha modificado el Stock del producto con el id " + idProductInt);
            }
        }
        dis.close();
    }

    //Mira si el fichero introducido tiene extension txt y se la pone.
    public static String hasExtensionTXT(String fileName){
        if(fileName.length()>4){
            //System.out.println(fileName.substring(fileName.length()-4, fileName.length()));
            if(!fileName.substring(fileName.length()-4, fileName.length()).equals(".txt")){
                System.out.println("No tiene .txt");
                fileName = fileName + ".txt";
            } else {
                return fileName;
            }
        } else {
            System.out.println("No tiene .txt");
            fileName = fileName + ".txt";
        }

        return fileName;
    }

    //Menu Clientes
    public static void customersMenu(CustomersDAO customers){
        char option = ' ';

        do {
            System.out.println("\nProductos\n");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Buscar Cliente");
            System.out.println("3. Modificar Cliente");
            System.out.println("4. Borrar Cliente");
            System.out.println("5. Mostrar todos los Clientes\n");
            System.out.println("0. Salir");
            System.out.println("Opcion: \n");

            try {
                option = scan.next().charAt(0); 
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

            switch (option) {
                case '1':
                    //add customer
                    Customer newCustomer = createCustomer();
                    if(customers.add(newCustomer)==null){
                        System.out.println("Se ha añadido correctamente.");
                    } else {
                        System.out.println("No se ha podido añadir.");
                    }
                    break;

                case '2':
                    //Encontrar Cliente
                    searchCustomer(customers);
                    break;

                case '3':
                    //Modificar Cliente
                    modifyCustomer(customers);
                    break;

                case '4':
                    //Eliminar Cliente
                    deleteCustomer(customers);
                    break;

                case '5':
                    //Mostrar Clientes
                    showCustomers(customers);
                    break;

                case '0':
                    System.out.println("Adios!");
                    break;
            
                default:
                    System.out.println("Opcion Incorrecta");
                    break;
            }

        } while (option!='0');
    }

    //Menu Proveedores
    public static void providersMenu(ProvidersDAO providers){
        char option = ' ';

        do {
            System.out.println("\nProductos\n");
            System.out.println("1. Agregar Proveedor");
            System.out.println("2. Buscar Proveedor");
            System.out.println("3. Modificar Proveedor");
            System.out.println("4. Borrar Proveedor");
            System.out.println("5. Mostrar todos los Proveedores\n");
            System.out.println("0. Salir");
            System.out.println("Opcion: \n");

            try {
                option = scan.next().charAt(0);
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

            switch (option) {
                case '1':
                    Provider newProvider = createProvider();
                    if(providers.add(newProvider)==null){
                        System.out.println("Se ha añadido correctamente.");
                    } else {
                        System.out.println("No se ha podido añadir.");
                    }                     
                    break;

                case '2':
                    searchProvider(providers);
                    break;

                case '3':
                    modifyProvider(providers);
                    break;

                case '4':
                    deleteProvider(providers);
                    break;

                case '5':
                    showProviders(providers);
                    break;

                case '0':
                    System.out.println("Adios!");
                    break;
            
                default:
                    System.out.println("Opcion Incorrecta");
                    break;
            }

        } while (option!='0');
    }

    /*
    FUNCIONES PACKS Y PRODUCTOS
    */
    //Funcion para añadir un pack
    private static void addPack(ProductsDAO products) {
        Pack newPack = createPack(products);
        if(newPack != null){
            if(products.add(newPack)!=null){
                System.out.println("\nEl producto se ha añadido correctamente\n");
            } else{
                System.out.println("\nEl Producto ya existe y no se ha podido añadir\n");
            }
        } else {
            System.out.println("Ya existe un pack con esta lista de Productos");
        }


    }
    //Funcion para añadir un producto
    public static void addProduct(ProductsDAO products){
        Product newProduct = createProduct();

        if(products.add(newProduct)!=null){
            System.out.println("\nEl producto se ha añadido correctamente\n");
        } else{
            System.out.println("\nEl Producto ya existe y no se ha podido añadir\n");
        }

    }
    //Funcion para enseñar todos los productos
    public static void showProducts(ProductsDAO products){
        System.out.println("\nMOSTRAR PRODUCTOS: \n");
        System.out.println(showMap(products)); 
    }
    //Funcion para buscar un producto
    public static void searchProduct(ProductsDAO products){
        System.out.println("\nBUSCAR PRODUCTO: \n");
        System.out.println("Introduce el id del producto que quieres buscar: ");
        int id = 0;
        try {
            id = scan.nextInt();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        if(products.search(id)!=null){
            System.out.println(products.search(id));
        } else{
            System.out.println("El producto no existe, no se puede encontrar");
        }

    }
    //Funcion para modificar un producto o Pack
    public static void modifyProduct(ProductsDAO products){
        System.out.println("\nMODIFICAR PRODUCTO: \n");
        System.out.println("Que quieres modificar? ");
        System.out.println("1. Producto");
        System.out.println("2. Pack");
        int opc = 0;
        try {
            opc = scan.nextInt();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        if(opc==1){
            modificarProducto(products);
        } else {
            modificarPack(products);
        }
        
    }
    //Funcion para modificar Productos
    public static void modificarProducto(ProductsDAO products){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Introduce el ID del producto: ");
        int id = 0;
        try {
            id = scan.nextInt();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        if(products.search(id)!=null){
            System.out.println(products.search(id)+"\n");

            System.out.println("Introduce el nuevo nombre: ");
            scan.nextLine();
            String name = scan.nextLine();
            System.out.println("Introduce el nuevo precio: ");
            Float price = scan.nextFloat();
            System.out.println("Introduce el nuevo Stock");
            int stock = scan.nextInt();
            System.out.println("Introduce la fecha de inicio: ");
            String firtsDateS = scan.next();
            LocalDate firtsDate = LocalDate.parse(firtsDateS, dtf);
            System.out.println("Introduce la fecha final:");
            String lastDateS = scan.next();
            LocalDate lastDate = LocalDate.parse(lastDateS, dtf);


            Product p = new Product(id, name, price, stock, firtsDate, lastDate);

            products.getMap().replace(id, p);

            System.out.println(""+products.search(id));
        }else {
            System.out.println("El producto no se ha encontrado, no existe.");
        }
    }
    //Funcion para modificar Packs
    public static void modificarPack(ProductsDAO products){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Introduce el ID del Pack: ");
        int id = 0;
        try {
            id = scan.nextInt();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }


        if(products.search(id)!=null){
            System.out.println(products.search(id)+"\n");

            System.out.println("Introduce el nombre del Pack: ");
            scan.nextLine();
            String name = scan.nextLine();
            System.out.println("Introduce el precio del Pack");
            Float price = scan.nextFloat();
            System.out.println("Introduce el stock del Pack");
            int stock = scan.nextInt();
            System.out.println("Cuantos Productos vas a introducir en el pack? ");
            int numProds = scan.nextInt();
            TreeSet<Product> productList = new TreeSet<Product>();
            for(int i =0; i<numProds; i++){
                System.out.println("Introduce el ID del producto a introducir en el pack: ");
                int productId = scan.nextInt();
                Product productForPack = products.search(productId);
                if(productForPack!=null){
                    productList.add(productForPack);
                } else {
                    do{
                        System.out.println("El producto no se ha encontrado vuelve a introducir el ID: ");
                        productId = scan.nextInt();
                        productForPack = products.search(productId);
                    }while(productForPack==null);
                    productList.add(productForPack);
                }
                
                System.out.println(productList);
            }
            System.out.println("Introduce el descuento del pack");
            int discount = scan.nextInt();
            System.out.println("Introduce la fecha de inicio: ");
            String firtsDateS = scan.next();
            LocalDate firtsDate = LocalDate.parse(firtsDateS, dtf);
            System.out.println("Introduce la fecha final:");
            String lastDateS = scan.next();
            LocalDate lastDate = LocalDate.parse(lastDateS, dtf);
    
            Pack p = new Pack(id, name, price, stock, productList, discount, firtsDate, lastDate);

            products.getMap().replace(id, p);

            System.out.println(""+products.search(id));
        }else {
            System.out.println("El producto no se ha encontrado, no existe.");
        }
    }
    //Funcion para eliminar un producto
    public static void deleteProduct(ProductsDAO products){
        System.out.println("\nELIMINAR PRODUCTO: \n");
        System.out.println("Introduce el id del producto que quieres eliminar");
        int id = scan.nextInt();

        if(products.delete(id)!=null){
            System.out.println("El producto se ha eliminado correctamente");
        } else {
            System.out.println("El producto no existe, no se ha podido eliminar");
        }
    }
    //Funcion para crear un producto
    public static Product createProduct(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\nAÑADIR PRODUCTO: \n");
        System.out.println("Introduce el ID del producto:");
        int idProduct = scan.nextInt();
        System.out.println("El nombre del producto: ");
        scan.nextLine();
        String name = scan.nextLine(); 
        System.out.println("El precio");
        Float price = scan.nextFloat();
        System.out.println("Stock");
        int stock = scan.nextInt();
        System.out.println("Introduce la fecha de inicio: ");
        String firtsDateS = scan.next();
        LocalDate firtsDate = LocalDate.parse(firtsDateS, dtf);
        System.out.println("Introduce la fecha final:");
        String lastDateS = scan.next();
        LocalDate lastDate = LocalDate.parse(lastDateS, dtf);

        return new Product(idProduct, name, price, stock, firtsDate, lastDate);
    }
    //Funcion para crear un pack
    public static Pack createPack(ProductsDAO products){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\nAÑADIR PACK: \n");

        System.out.println("Introduce el ID del Pack: ");
        int idProduct = scan.nextInt();
        System.out.println("Introduce el nombre del Pack: ");
        scan.nextLine();
        String name = scan.nextLine();
        System.out.println("Introduce el precio del Pack");
        Float price = scan.nextFloat();
        System.out.println("Introduce el stock del Pack");
        int stock = scan.nextInt();
        System.out.println("Cuantos Productos vas a introducir en el pack? ");
        int numProds = scan.nextInt();
        TreeSet<Product> packProductList = insertProductsToPack(products, numProds);
        System.out.println("Introduce el descuento del pack");
        int discount = scan.nextInt();
        System.out.println("Introduce la fecha de inicio: ");
        String firtsDateS = scan.next();
        LocalDate firtsDate = LocalDate.parse(firtsDateS, dtf);
        System.out.println("Introduce la fecha final:");
        String lastDateS = scan.next();
        LocalDate lastDate = LocalDate.parse(lastDateS, dtf);

        Pack p = new Pack(idProduct, name, price, stock, packProductList, discount, firtsDate, lastDate);

        if(!hasSameProductList(products, p)){
            return p;
        } else {
            return null;
        }
    }

    public static TreeSet<Product> insertProductsToPack(ProductsDAO products, int numProds){
        TreeSet<Product> packProductList = new TreeSet<Product>();
        for(int i =0; i<numProds; i++){
            System.out.println("Introduce el ID del producto a introducir en el pack: ");
            int productId = scan.nextInt();
            Product productForPack = products.search(productId);
            if(productForPack!=null){
                if(!packProductList.contains(productForPack)){
                    packProductList.add(productForPack);
                } else {
                    System.out.println("El producto ya estaba añadido");
                }
            } else {
                do{
                    System.out.println("El producto no se ha encontrado vuelve a introducir el ID: ");
                    productId = scan.nextInt();
                    productForPack = products.search(productId);
                }while(productForPack==null);
                packProductList.add(productForPack);
            }
        }
        return packProductList;
    }

    public static boolean hasSameProductList(ProductsDAO products, Pack p){
        HashMap map = products.getMap();
        boolean hasSameProductList = false;

        for(Object value : map.values()){
            if(value instanceof Pack){
                if(value.equals(p)){
                    hasSameProductList = true;
                    break;
                }
            }
        }

        return hasSameProductList;
    }

    /* 
    FUNCIONES CLIENTES
    */
    //Funcion para crear un cliente
    public static Customer createCustomer(){
        System.out.println("\nAÑADIR CLIENTE: \n");

        System.out.println("Introduce el ID: ");
        int id = scan.nextInt();
        System.out.println("Introduce el DNI:");
        String dni = scan.next();
        scan.nextLine();
        System.out.println("Introduce el nombre: ");
        String name = scan.nextLine();
        System.out.println("Introduce los apellidos: ");
        String lastName = scan.nextLine();
        System.out.println("Introduce la fecha de nacimiento(2001-09-01)");
        LocalDate birthDate = LocalDate.parse(scan.nextLine());
        System.out.println("Introduce el email: "); 
        String email = scan.nextLine();
        System.out.println("Introduce el telefono: ");
        String phone = scan.nextLine();
        
        return new Customer(id, dni, name, lastName, birthDate, email, phone);
    }
    //Funcion para encontrar un cliente
    public static void searchCustomer(CustomersDAO customers){        
        System.out.println("Introduce el ID del cliente que quieres encontrar: ");
        int id = scan.nextInt();
        System.out.println(customers.search(id)); 
    }
    //Funcion para Modificar cliente
    public static void modifyCustomer(CustomersDAO customers){
        System.out.println("Introduce el ID del cliente que quieres modificar: ");
        int id = scan.nextInt();

        if(customers.search(id)!=null){
            System.out.println(customers.search(id)+"\n");

            System.out.println("Introduce el DNI");
            String dni = scan.next();
            scan.nextLine();
            System.out.println("Introduce el nombre: ");
            String name=scan.next();
            scan.nextLine();
            System.out.println("Introduce los apellidos: ");
            String lastName = scan.nextLine();
            System.out.println("Introduce la fecha de nacimiento");
            LocalDate birthDate = LocalDate.parse(scan.nextLine());
            System.out.println("Introduce el email: "); 
            String email = scan.nextLine();
            System.out.println("Introduce el telefono: ");
            String phone = scan.nextLine();
            
            Customer c = new Customer(id, dni, name, lastName, birthDate, email, phone);
            
            customers.getMap().replace(id, c);

            System.out.println(""+customers.search(id));
        }else {
            System.out.println("El cliente no se ha encontrado, no existe.");
        }
    }
    //Funcion para eliminar cliente
    public static void deleteCustomer(CustomersDAO customers){
        System.out.println("Introduce el ID del cliente que quieres eliminar: ");
        int id = scan.nextInt();

        if(customers.delete(id)!=null){
            System.out.println("El cliente se ha eliminado correctamente");
        } else {
            System.out.println("No se ha podido eliminar el cliente");
        }
    }
    //Funcion para mostrar todos los clientes
    public static void showCustomers(CustomersDAO customers){
        System.out.println("\nLista de Clientes: \n");
        System.out.println(showMap(customers));
    }
    
    /*
    FUNCIONES PROVIDERS
    */
    //Funcion para crear un proveedor
    public static Provider createProvider(){
        System.out.println("\nAÑADIR PROVEEDOR: \n");

        System.out.println("Introduce el ID: ");
        int id = scan.nextInt();
        System.out.println("Introduce el nombre: ");
        scan.nextLine();
        String name = scan.nextLine();
        System.out.println("Introduce el nif: ");
        String nif = scan.next();
        System.out.println("Introduce el telefono: ");
        String phone = scan.next();

        return new Provider(id, name, nif, phone);
    }
    //Funcion para encontrar un cliente
    public static void searchProvider(ProvidersDAO providers){      
        System.out.println("Introduce el ID del proveedor que quieres encontrar: ");
        int id = scan.nextInt();
        System.out.println(providers.search(id)); 
    }
    //Funcion para Modificar cliente
    public static void modifyProvider(ProvidersDAO providers){
        System.out.println("Introduce el ID del proveedor que quieres modificar: ");
        int id = scan.nextInt();

        if(providers.search(id)!=null){
            System.out.println(providers.search(id)+"\n");

            System.out.println("Introduce el NIF: ");

            String nif = scan.next();
            System.out.println("Introduce el nombre: ");
            String name = scan.next();
            System.out.println("Introduce el telefono: ");
            String phone = scan.next();
    
            Provider p = new Provider(id, name, nif, phone);

            providers.getMap().replace(id, p);

            System.out.println(""+providers.search(id));
        }else {
            System.out.println("El proveedor no se ha encontrado, no existe.");
        }
    }
    //Funcion para eliminar cliente
    public static void deleteProvider(ProvidersDAO providers){
        System.out.println("Introduce el ID del Proveedor que quieres eliminar: ");
        int id = scan.nextInt();

        if(providers.delete(id)!=null){
            System.out.println("El proveedor se ha eliminado correctamente");
        } else {
            System.out.println("No se ha podido eliminar el proveedor");
        }
    }
    //Funcion para mostrar todos los clientes
    public static void showProviders(ProvidersDAO providers){
        System.out.println("\nLista de Proveedores: \n");
        System.out.println(showMap(providers));        
    }

    //Devuelve los objetos de un DAO 
    public static Collection showMap(Persistable obj){
        return obj.getMap().values();
    }

}
