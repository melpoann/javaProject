package database;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by oleyn on 19.11.2016.
 */
public class Main {

    private static String FullName;
    private static int PhoneNumber;
    private static int Id;
    private static String Adress;

    private static String insert;
    private static String select;
    private static String update;
    private static String delete;

    private static String createTab;


    public static void main(String[] args) {
        try {
            String str = "jdbc:postgresql://ec2-54-75-230-140.eu-west-1.compute.amazonaws.com:5432/d873psuel7u23f?user=qmpdylwkzkqfzu&password=ktx5HbvGZuZcY2Fah846lqb3E8&sslmode=require";
            Connection conn = DriverManager.getConnection(str);
            try {
                Statement create = conn.createStatement();
                //test = "INSERT INTO  positions (FullName, Adress, PhoneNumber) VALUES ('Рик', 'апарара','12345')";
                //createTab = "CREATE TABLE positions ( IdPositions SERIAL PRIMARY KEY,  FullName VARCHAR(30) NOT  NULL , PhoneNumber VARCHAR (10)NOT  NULL, Adress VARCHAR(30) NOT  NULL)";
                //create.execute(createTab);
                //System.out.print("Заработало!");
                //create.close();
                System.out.println("Выберете действие:\n 1.Добавить запись\n 2.Вывести все записи\n 3.Редактировать запись\n 4.Удалить запись");
                Scanner s = new Scanner(System.in);
                int i = s.nextInt();
                switch (i) {
                    case 1:
                        System.out.println("Введите полное имя: ");
                        Scanner s1 = new Scanner(System.in);
                        FullName = new String(s1.nextLine());
                        System.out.println("Введите номер телефона (не более 10 символов): ");
                        Scanner s2 = new Scanner(System.in);
                        PhoneNumber = s2.nextInt();
                        System.out.println("Введите адрес: ");
                        Scanner s3 = new Scanner(System.in);
                        Adress = new String(s3.nextLine());
                        try {
                            Statement add = conn.createStatement();
                            insert = "insert into positions (FullName, PhoneNumber, Adress) values ("+"'"+FullName + "'," + PhoneNumber + ",'" + Adress + "');";
                            add.execute(insert, Statement.RETURN_GENERATED_KEYS);
                            ResultSet keyset = add.getGeneratedKeys();
                            System.out.println("Новоя позиция добавлена!"+"Id добавленной записи:"+keyset);
                            add.close();
                        } catch (SQLException e) {
                            System.err.println("Новоя позиция не добавлена!"+e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            Statement sel = conn.createStatement();
                            select = "select * from positions ";
                            ResultSet resSel = sel.executeQuery(select);
                            while (resSel.next())
                            {
                                Id = resSel.getInt("IdPositions");
                                FullName = resSel.getString("FullName");
                                PhoneNumber = resSel.getInt("PhoneNumber");
                                Adress = resSel.getString("Adress");
                                System.out.println("Id : " + Id);
                                System.out.println("Имя : " + FullName);
                                System.out.println("Телефон : " + PhoneNumber);
                                System.out.println("Адрес : " + Adress);
                            }
                            System.out.println("Все элементы выведены!");
                        }
                        catch (SQLException e) {
                            System.err.println("Записи не получены"+e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Введите id редактируемой позиции: ");
                        Scanner s4 = new Scanner(System.in);
                        Id = s4.nextInt();
                        try {
                            Statement upd = conn.createStatement();
                            select = "select * from positions WHERE IdPositions=" + Id;
                            ResultSet resUpd = upd.executeQuery(select);
                            System.out.println("Найдена запись: ");
                            while (resUpd.next()) {
                                Id = resUpd.getInt("IdPositions");
                                FullName = resUpd.getString("FullName");
                                PhoneNumber = resUpd.getInt("PhoneNumber");
                                Adress = resUpd.getString("Adress");
                                System.out.println("Id : " + Id);
                                System.out.println("Имя : " + FullName);
                                System.out.println("Телефон : " + PhoneNumber);
                                System.out.println("Адрес : " + Adress);
                            }
                            System.out.println("Введите новое полное имя: ");
                            Scanner s5 = new Scanner(System.in);
                            FullName = new String(s5.nextLine());
                            System.out.println("Введите новый  номер телефона (не более 10 символов): ");
                            Scanner s6 = new Scanner(System.in);
                            PhoneNumber = s6.nextInt();
                            System.out.println("Введите новый  адрес: ");
                            Scanner s7 = new Scanner(System.in);
                            Adress = new String(s7.nextLine());
                            try {
                                update = "UPDATE positions SET FullName="+"'"+FullName+"', PhoneNumber="+PhoneNumber+",Adress="+"'"+Adress+"' WHERE IdPositions="+Id;
                                upd.execute(update);
                                System.out.println("Позиция изменена!");
                                upd.close();
                            } catch (SQLException e) {
                                System.err.println("Позиция не изменена!" + e.getMessage());
                            }
                        }
                        catch (SQLException e) {
                            System.err.println("Позиция не изменена!" + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.println("Введите id удаляемой позиции: ");
                        Scanner s8 = new Scanner(System.in);
                        Id = s8.nextInt();
                        try {
                            Statement del = conn.createStatement();
                            delete = "delete from POSITIONS WHERE IdPositions=" + Id;
                            del.execute(delete);
                            System.out.println("Позиция удалена!");

                        }
                        catch (SQLException e) {
                            System.err.println("Позиция не удалена!" + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Неверный номер операции!");

                        conn.close();
                }

            } catch (SQLException e) {
                System.err.println("Таблица не создана!"+e.getMessage());
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Не найден jdbc драйвер!");
        }
    }
}
