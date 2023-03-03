package sample;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinderScreen extends JFrame{
    private JProgressBar progressBar1;
    private JButton btnOk;
    private JLabel lblProgres;
    private JLabel lblFound;
    private JPanel mainPanel;

    int progres;
    String file;

    public FinderScreen(final LandingPage lp, String password, int threads){

        Runnable setUI = new Runnable() {
            @Override
            public void run() {
                setSize(1200, 700);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setVisible(true);
                setTitle("Finder page");
                setParameters();
            }
        };
        Thread ne = new Thread(setUI);
        ne.setPriority(Thread.MAX_PRIORITY);
        ne.start();

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lp.setVisible(true);
                setVisible(false);
            }
        });
        progressBar1.setMinimum(0);
        progressBar1.setMaximum(17);
        final Finder finder = new Finder(password, threads);
        finder.find();



        Runnable getProgres = new Runnable() {
            @Override
            public void run() {
                while (progres<16){
                    progres = finder.getProgres();
                    progressBar1.setValue(progres);
                    file = finder.getFileF();
                    System.out.println("progres:  " + progres);
                    if (progres==17){
                        if (file == null)lblFound.setText("Tvoje heslo som nenašiel");
                        else{
                            lblFound.setText("Tvoje heslo som našiel v súbore: " + file);
                            System.out.println("Tvoje heslo som našiel v súbore: " + file);
                        }
                        Thread.currentThread().stop();
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        };
        Thread dd = new Thread(getProgres);
        dd.start();

    }

    private void setParameters(){
        setContentPane(mainPanel);
        lblProgres.setText("Progres: ");
        lblFound.setText("Hľadám");
        btnOk.setText("Späť");
    }
}
