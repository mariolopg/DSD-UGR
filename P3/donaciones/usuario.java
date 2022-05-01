public class usuario {
    private String userName, password;
    // private int numDonaciones;
    // private int totalDonado;

    public usuario(String userName, String password){
        this.userName = userName;
        this.password = password;
        // this.numDonaciones = 0;
        // this.totalDonado = 0;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    // public int numDonaciones(){
    //     return numDonaciones;
    // }

    // public int totalDonado(){
    //     return totalDonado;
    // }

    // public boolean donar(int cantidad){
    //     if(cantidad > 0){
    //         numDonaciones++;
    //         totalDonado += cantidad;
    //         return true;
    //     }

    //     return false;
    // }
}
