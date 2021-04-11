/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.uni.programacion.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ni.edu.uni.programacion.backend.dao.implementation.JsonVehicleDaoImpl;
import ni.edu.uni.programacion.backend.pojo.Vehicle;
import ni.edu.uni.programacion.views.panels.InternalFrameViewVehicles;
import ni.edu.uni.programacion.views.panels.DialogNew;
import ni.edu.uni.programacion.controllers.PnlVehicleController;
import ni.edu.uni.programacion.backend.dao.implementation.JsonVehicleDaoImpl;

/**
 *
 * @author Sistemas-05
 */
public class PnlViewVehicleController implements ActionListener{
    
    private DialogNew dialogNew;
    private PnlVehicleController pnlVehicleController;
    private InternalFrameViewVehicles internalFrameViewVehicles;
    private JsonVehicleDaoImpl jsonVehicleDaoImpl;
    private DefaultTableModel tblViewModel;
    private List<Vehicle> vehicles;
    private String[] HEADERS = new String[]{"StockNumber", "Year", "Make", "Model", "Style",
        "Vin", "Exterior color", "Interior color", "Miles", "Price", "Transmission", "Engine", "Image", "Status"};
    private TableRowSorter<DefaultTableModel> tblRowSorter;
    private JsonVehicleDaoImpl JDao;

    public PnlViewVehicleController(InternalFrameViewVehicles internalFrameViewVehicles) {
        this.internalFrameViewVehicles = internalFrameViewVehicles;
        initComponent();
    }

    private void initComponent() {
	internalFrameViewVehicles.getBtnUpdate().addActionListener(this);
	internalFrameViewVehicles.getBtnDelete().addActionListener(this);
	internalFrameViewVehicles.getBtnNew().addActionListener(this);
        try {
            jsonVehicleDaoImpl = new JsonVehicleDaoImpl();

            loadTable();

            internalFrameViewVehicles.getTxtFinder().addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    txtFinderKeyTyped(e);
                }
            });

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PnlViewVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PnlViewVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void txtFinderKeyTyped(KeyEvent e) {
        RowFilter<DefaultTableModel, Object> rf = null;
        rf = RowFilter.regexFilter(internalFrameViewVehicles.getTxtFinder().getText(), 0, 1, 2, 3, 4, 5, 6, 7, 8);
        tblRowSorter.setRowFilter(rf);
    }

    private void loadTable() throws IOException {
        tblViewModel = new DefaultTableModel(getData(), HEADERS);
        tblRowSorter = new TableRowSorter<>(tblViewModel);

        internalFrameViewVehicles.getTblViewVehicle().setModel(tblViewModel);
        internalFrameViewVehicles.getTblViewVehicle().setRowSorter(tblRowSorter);
    }

    private Object[][] getData() throws IOException {
        vehicles = jsonVehicleDaoImpl.getAll().stream().collect(Collectors.toList());
        Object data[][] = new Object[vehicles.size()][vehicles.get(0).asArray().length];

        IntStream.range(0, vehicles.size()).forEach(i -> {
            data[i] = vehicles.get(i).asArray();
        });

        return data;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("Nuevo")){
			
		}
		if (e.getActionCommand().equalsIgnoreCase("Actualizar")){
			
		}
		if (e.getActionCommand().equalsIgnoreCase("Eliminar")){
			int fila = internalFrameViewVehicles.getTblViewVehicle().getSelectedRow();
			if (fila >= 0){
                            try {
                                int stock, year;
                                String make,model, style, vin, eColor, iColor, miles, engine, image, status;
                                float price;
                                Vehicle.Transmission transmission;
                                stock = (int) internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 0);
                                year = (int) internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 1);
                                make = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 2));
                                model = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 3));
                                style = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 4));
                                vin = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 5));
                                eColor = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 6));
                                iColor = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 7));
                                miles = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 8));
                                price = (float) internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 9);
                                transmission = (Vehicle.Transmission) internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 10);
                                engine = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 11));
                                image = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 12));
                                status = String.valueOf(internalFrameViewVehicles.getTblViewVehicle().getValueAt(fila, 13));
                                Vehicle v = new Vehicle(stock, year, make, model, style, vin,
                                        eColor, iColor, miles, price, transmission, engine, image, status);
                                
                                //tblViewModel.removeRow(Fila);  Método de eliminar fila
                                boolean delete = false;
                                        
                                delete = JDao.delete(v);
                                
                                if (!(delete = true)) {
                                } else {
                                    JOptionPane.showMessageDialog(null, "Archivo eliminado con éxito");
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(PnlViewVehicleController.class.getName()).log(Level.SEVERE, null, ex);
                            }      
			}
			else {
				JOptionPane.showMessageDialog(null, "Debe seleccionar un objeto a eliminar");
			}
		}
	}
}