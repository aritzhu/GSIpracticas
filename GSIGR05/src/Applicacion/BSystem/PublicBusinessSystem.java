package Applicacion.BSystem;
import GSILabs.connect.AdminGateway;
import GSILabs.connect.ClientGateway;
import Dominio.IBModelo.Local;
import Dominio.BModel.Review;
import Dominio.BModel.Cliente;
import Dominio.BModel.Restaurante;
import Dominio.BModel.Bar;
import Dominio.IBModelo.Usuario;
import Dominio.BModel.Contestacion;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PublicBusinessSystem extends BusinessSystem 
    implements AdminGateway, ClientGateway {
    
    private EjecuctionTimeDataBase database = this.getDatabase();

    public PublicBusinessSystem() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }

    // ========== IMPLEMENTACIÓN DE LocalFinder ==========
    
    @Override
    public Local getLocal(String name) throws RemoteException {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        // Buscar local exacto por nombre
        for (Local local : this.database.getLocales()) {
            if (local.getNombre() != null && 
                local.getNombre().equalsIgnoreCase(name.trim())) {
                return local;
            }
        }
        return null;
    }
    
    @Override
    public Local[] getLocals(String name) throws RemoteException {
        if (name == null || name.trim().isEmpty()) {
            return new Local[0];
        }
        List<Local> resultados = new ArrayList<>();
        String searchName = name.trim().toLowerCase();
        
        for (Local local : this.database.getLocales()) {
            if (local.getNombre() != null && 
                local.getNombre().toLowerCase().contains(searchName)) {
                resultados.add(local);
            }
        }
        return resultados.toArray(new Local[0]);
    }

    // ========== IMPLEMENTACIÓN DE AdminGateway ==========
    
    // hay que overridear las funciones que tengamos de BusinessSystem y llamarlas aqui
    
    @Override
    public Boolean eliminarLocal(Local l) throws RemoteException {
        
    }
    
    @Override
    public Boolean eliminaReviewsDeLocal(Local l) throws RemoteException {
        
    }
    
    @Override
    public Boolean eliminaReview(Review r) throws RemoteException {
        
    }
            
    @Override
    public Integer eliminaReviewsDeUsuario(Cliente c) throws RemoteException {
        
    }
            
    @Override
    public Boolean insertaReviewFalsa(Local l, Integer puntuacion) throws RemoteException {
        
    }

    // ========== IMPLEMENTACIÓN DE ClientGateway ==========
    
    @Override
    public boolean nuevaReview(Review r) throws RemoteException {
        
    }
    
    // Hay que ver bien esto, porque hay una arriba eliminarReview() 
    // (no se si es la misma o que pretende con este ejercicio)
    @Override
    public boolean quitaReview(Review r) throws RemoteException {
        
    }
    
    @Override
    public Bar mejorBar(String ciudad) throws RemoteException {
        
    }
    
    @Override
    public Restaurante[] mejoresRestaurantes(String ciudad, Integer num) throws RemoteException {
        
    }
}