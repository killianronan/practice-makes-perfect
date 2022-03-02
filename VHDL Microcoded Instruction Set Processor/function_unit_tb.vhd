library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity function_unit_tb is
end function_unit_tb;

architecture Behavioral of function_unit_tb is
    component function_unit is
        Port (
            A : in std_logic_vector(15 downto 0);
            B : in std_logic_vector(15 downto 0);
            FS : in std_logic_vector(4 downto 0);
            V : out std_logic;
            C : out std_logic;
            N : out std_logic;
            Z : out std_logic;    
            F : out std_logic_vector(15 downto 0)
        );
    end component;    
    --inputs    
    signal A, B : std_logic_vector(15 downto 0) := x"0000";
    signal FS : std_logic_vector(4 downto 0) := "00000";
    --outputs
    signal F : std_logic_vector(15 downto 0) := x"0000";  
    signal V,C,N,Z : std_logic := '0'; 
        
begin
    UUT: function_unit
    Port Map(
        A => A,
        B => B,
        FS => FS,
        F => F,
        V => V,
        C => C,
        N => N, 
        Z => Z
    );
    
sim_proc :process
begin
        --Arithmetic Tests:
        --F=A
        FS <= "00000";
       	A <= x"0002";
       	B <= x"0021";
        wait for 15ns;
        
        --F=A+1
        FS <= "00001";
       	A <= x"0008";
       	B <= x"0090";
        wait for 15ns;
        
       --F=A+B
      	FS <= "00010";
       	A <= x"0001";
       	B <= x"0001";
        wait for 15ns;
        
       --F=A+B+1
       	FS <= "00011";
       	A <= x"0002";
       	B <= x"0002";
        wait for 15ns;
        
       --F=A+B'
       	FS <= "00100";
       	A <= x"0010";
       	B <= x"FFEF";
        wait for 15ns;
        
        --F=A+B'+1
       	FS <= "00101";
       	A <= x"0010";
       	B <= x"FFFE";
        wait for 15ns;
        
        --F=A-1
       	FS <= "00110";
       	A <= x"0009";
       	B <= x"0222";
        wait for 15ns;
        
        --Logic Tests:
        --F=A
       	FS <= "00111";
       	A <= x"0333";
       	B <= x"0222";
        wait for 15ns;
        
        --F=A^B
        FS <= "01000";
       	A <= x"0101";
       	B <= x"0111";
        wait for 15ns;
        
        --F=AorB
        FS <= "01010";
       	A <= x"0101";
       	B <= x"1010";
        wait for 15ns;
        
       --F=A xor bits
      	FS <= "01100";
       	A <= x"0FF0";
       	B <= x"FFFF";
        wait for 15ns;
        
       --F=A'
       	FS <= "01110";
       	A <= x"EEEE";
       	B <= x"0003";
        wait for 15ns;
                
        --F=B
        FS <= "10000";
        A <= x"0001";
        B <= x"00F0";
        wait for 15ns;
        
        --F= shift right
        FS <= "10100";
        A <= x"0010";
        B <= x"0010";
        wait for 15ns;
        
       --F= shift left
        FS <= "11000";
        A <= x"0010";
        B <= x"0010";
        wait for 15ns;        
end process;
    
end Behavioral;
