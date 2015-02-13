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
    //Cantidad de entradas en un determinado dia.
    private int[] dayCounts;
    //Cantidad de accesos exitosos por dia.
    private int[] succesfullAccesHour;
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
        
        dayCounts = new int[32];
        
        succesfullAccesHour = new int[24];
        
        hourlyused = false;
    }
    /**
     * Constructor con el nombre de archivo a analizar como parametro
     */
    public LogAnalyzer(String nombre){
        hourCounts = new int[24];
        dayCounts = new int[32];
        succesfullAccesHour = new int[24];
        
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
     * Analiza los accesos por dia del mes
     */
    public void analyzeDailyData(){
        while(reader.hasNext()){
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
    }
    /**
     * Analiza los accesos correctos por hora
     */
    public void analyzeSuccesfullHour(){
        while(reader.hasNext()){
            LogEntry entry = reader.next();
            if(entry.getStatus() == 200){
                int hour = entry.getHour();
                succesfullAccesHour[hour]++;
            }
        }
    }
    /**
     * Imprime por pantalla la cantidad de accesos conseguidos por hora
     * 
     */
    public void printSuccesfullAccess(){
        int cont = 0;
        while(cont < succesfullAccesHour.length){
            System.out.println("Hora " + cont + ": " + succesfullAccesHour[cont] + " accesos.");
            cont++;
        }
    }
    /**
     * Imprime por pantalla la cantidad de accesos por dia del mes
     */
    public void printDailyCounts(){
        int cont = 1;
        while(cont<dayCounts.length){
            System.out.println("Dia " + cont + ": " + dayCounts[cont] + " accesos.");
            cont++;
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
    
    /**
     * Analyze the  hourly accesses in  the given date
     * 
     * @param day The given day
     * @param month The given month
     * @param year The given year
     */
    public void analyzeDate(int year, int month, int day){
       while(reader.hasNext()){
           LogEntry entry = reader.next();
           if((year == entry.getYear()) && (month == entry.getMonth()) && (day == entry.getDay())){
               int hour = entry.getHour();
               hourCounts[hour]++;
            }
        }
    }
}
