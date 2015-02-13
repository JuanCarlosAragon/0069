/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    //Define cuando ha sido usado el metodo analyzeHourlyData
    private boolean hourlyused;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
        
        hourlyused = false;
    }
    /**
     * Constructor con el nombre de archivo a analizar como parametro
     */
    public LogAnalyzer(String nombre){
        LogfileCreator creator = new LogfileCreator();
        creator.createFile(nombre,10);
        hourCounts = new int[24];
        reader = new LogfileReader(nombre);
        
        hourlyused = false;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        hourlyused = true;
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    /**
     * Devuelve el numero total de accesos al servidor registrados 
     * en el archivo.
     */
    public int numberOfAccesses(){
        int contAcces = 0;
        int contHour = 0;
        if(hourlyused){
            while(contHour < hourCounts.length){
                contAcces += hourCounts[contHour];
                contHour++;
            }
        }
        return contAcces;
        
    }
    /**
     * Devuelve la hora en la que se han registrado mas entradas al 
     * servidor
     */
    
    public int busiestHour(){
        int hour = 0;
        int cont = 0;
        int maxCount = 0;
        if(hourlyused){
            while(cont < hourCounts.length){
                if(hourCounts[cont]>maxCount){
                    maxCount = hourCounts[cont];
                    hour = cont;
                }
                cont++;
            }
            
        }
        return hour;
    }
    
    /**
     * Devuelve la hora en la que se registraron menos entradas al
     * servidor
     */
    public int quietestHour(){
        int hour = 0;
        int cont = 0;
        int minCount = hourCounts[busiestHour()];
     
        if(hourlyused){
            while(cont < hourCounts.length){
                if(hourCounts[cont] < minCount){
                    minCount = hourCounts[cont];
                    hour = cont;
                }
                cont++;
            }
        }
        return hour;
    }
    /**
     * Devuelve la hora en la que empezó el intervalo de dos horas
     * con mas accesos al servidor
     */
    public int busiestTwoHours(){
        int hour = 0;
        int cont = 0;
        int sumHour = 0;
        
        if(hourlyused){
            while(cont < (hourCounts.length - 1)){
                if((hourCounts[cont] + hourCounts[cont+1])>sumHour){
                    sumHour = hourCounts[cont] + hourCounts[cont+1];
                    hour = cont;
                }
                cont++;
            }
            
        }
        return hour;
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int cont = 0;
        while(cont < hourCounts.length){
            System.out.println(cont + ": " + hourCounts[cont]);
            cont++;
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    
}
