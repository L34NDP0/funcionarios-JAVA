import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    protected String nome;
    protected LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }
}

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void aumentarSalario(BigDecimal percentual) {
        BigDecimal aumento = salario.multiply(percentual).divide(new BigDecimal(100));
        this.salario = this.salario.add(aumento);
    }

    public String getDataNascimentoFormatada() {
        return dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public int getIdade() {
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }
    
    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getInstance(Locale.of("pt", "BR"));
        return "Nome: " + nome + ", Data de Nascimento: " + getDataNascimentoFormatada() + 
               ", Salário: R$ " + nf.format(salario) + ", Função: " + funcao;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        
        funcionarios.removeIf(f -> f.nome.equals("João"));

        funcionarios.forEach(System.out::println);
        
        funcionarios.forEach(f -> f.aumentarSalario(new BigDecimal("10")));
 
        
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
         
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(System.out::println);
        });
        
        System.out.println("\nAniversários de Outubro e Dezembro:");
        funcionarios.stream()
                .filter(f -> f.dataNascimento.getMonthValue() == 10 || f.dataNascimento.getMonthValue() == 12)
                .forEach(System.out::println);

    
        Funcionario maisVelho = Collections.max(funcionarios, Comparator.comparing(Funcionario::getIdade));
        System.out.println("\nFuncionário mais velho: " + maisVelho.nome + " - " + maisVelho.getIdade() + " anos");
        
        System.out.println("\nFuncionários em ordem alfabética:");
        funcionarios.stream().sorted(Comparator.comparing(f -> f.nome)).forEach(System.out::println);
  
        BigDecimal totalSalarios = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários: R$ " + NumberFormat.getInstance(Locale.of("pt", "BR")).format(totalSalarios));
        
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários divididos pelo salário mínimo:");
        funcionarios.forEach(f -> 
        { 
            BigDecimal multiplos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.nome + " ganha " + multiplos + " salários mínimos.");
        });
    }
}