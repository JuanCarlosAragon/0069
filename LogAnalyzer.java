/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    
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
