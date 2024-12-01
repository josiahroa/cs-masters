from numpy.fft import fft, ifft
from numpy import real, imag

def polynomial_multiply(a_coeff_list, b_coeff_list):
    # Return the coefficient list of the multiplication 
    # of the two polynomials 
    # Returned list must be a list of floating point numbers.
    # Please convert list from complex to reals by using the 
    # real function in numpy.
    # your code here
    padding = len(a_coeff_list) + len(b_coeff_list) - 1
    
    a_padded = a_coeff_list + [0] * (padding - len(a_coeff_list))
    b_padded = b_coeff_list + [0] * (padding - len(b_coeff_list))
    
    fft_a = fft(a_padded)
    fft_b = fft(b_padded)
    
    c = [0] * padding # result
    for i in range(len(fft_a)):
        c[i] = fft_a[i] * fft_b[i]
    
    
    c = real(ifft(c))    
    return c

# inputs sets a, b, c
# return True if there exist n1 in a, n2 in B such that n1+n2 in C
# return False otherwise
# number n which signifies the maximum number in a, b, c
# here is a useful reference to set data structure in python
# https://docs.python.org/3/tutorial/datastructures.html#sets
def check_sum_exists(a, b, c, n):
    a_coeffs = [0]*n
    b_coeffs = [0]*n 
    
    # convert sets a, b into polynomials as provided in the hint
    # a_coeffs and b_coeffs should contain the result
    # your code here

    for x in a:
        a_coeffs[x] = 1

    for x in b:
        b_coeffs[x] = 1
            
    # multiply them together
    print ("a_coeffs . ", a_coeffs)
    print ("b_coeffs . ", b_coeffs)
    c_coeffs = polynomial_multiply(a_coeffs, b_coeffs)
    print("Co-efficients of the testcase are")
    coeffs_copy = []
    for num in c_coeffs:
        if(abs(num-0) < abs(num-1)):
            coeffs_copy.append(0)
        elif(abs(num-1) < abs(num-2)):
            coeffs_copy.append(1)
        else:
            coeffs_copy.append(2)
    print(coeffs_copy)
    # use the result to solve the problem at hand
    # your code here
            
    # return True/False
    for x in c:
        if x < len(coeffs_copy) and coeffs_copy[x] > 0:
            return True



    