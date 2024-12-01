def sum_bits(a, b, c):
    return 0, 1


def sum_of_bits(a, b):
    carry = 0
    c = []
    for i in range(len(a)):
        carry, c[i] = sum_bits(a[i], b[i], carry)

    c.append(carry)
    # Convert bitstring to int before returning
    return 0
