# Cartfidential

Within the current landscape of digital commerce, there is no such progress and there are still the same problems of
scalability and performance with high traffic than some years ago. For this reason, we have decided to implement a new
type of ecommerce based on the [Blockchain](https://builtin.com/blockchain) technology, creating a decentralized network
to execute and store, in a decentralized way, the different processes of an ecommerce.

That's why you will be in charge of creating a very important part of it, the shopping cart.

## How it works?

The aim of this API is to manage a shopping cart of our ecommerce website. So, through it, the Frontend Team will be
able to request and create, update or delete any item in the current cart.

Every action of the API will generate a new _Block_ on the _Blockchain_ in order to create a history and persist the
information, so we can retrieve it later, but also keeps a history of which items have the user added to the cart.

You could find the API description on the OpenAPI description file.

### Workflow

The workflow of this API is as follows:

* Create

1. Create request is received
2. Check if the `id` for a Cart is not already used
    1. If it has been used we return error
    2. If it has not been used, we create the cart with the given item and generates a new Block to add to the
       Blockchain
3. Add the new _Block_ to the _Blockchain_ with the current information of this Cart

* Update

1. Update request is received
2. Check if the `id` for a Cart exists
    1. If not exists return error
    2. If exist retrieve it
3. Update the items in the cart and generate a new Block with the information
4. Add the new _Block_ to the _Blockchain_

* Delete

1. Delete request is received
2. Check if the `id` for a Cart exists
    1. If not exists return error
    2. If exist retrieve it
3. Remove cart information and create a new _Block_
4. Add the new _Block_ to the _Blockchain_

## Technical Considerations

Keep in mind the following:

1. Related with the Blockchain:
    1. The blockchain have a first block called 'genesis block', these block doesn't contain a reference of the previous
       block, usually is hardcoded on the software
    2. Every Block has multiple properties:
        1. timestamp: the timestamp for the moment when the block was created
        2. lastHash: hash of the previous block on the _blockchain_
        3. data: information we want to store in the block (in our case the votes)
        4. nonce: a unique number
        5. hash: a SHA256 string for the block, calculated concatenating the timestamp, lastHash, data and nonce.
    3. The implementation of the Blockchain must follow these contract:
        ```
         Blockchain
           /** Adds new block to blockchain */
           addBlock(block: Block): Block
           /** The new blockchain that is a candidate for replacing the current blockchain */
           replace(blockchain: Blockchain): boolean
           /**
            * Validates the chain by checking if:
            * - Genesis Block hash match given blockchain genesis block hash
            * - every element's last hash value matches previous block's hash
            * - data hasn't been tampered (which will produce a different hash value)
            */
           isValid(blockchain: Blockchain): boolean
      
         Block
           /** Generate the first block for the chain */
           static getGenesisBlock(): Block
           /** Generate the hash for the given block */
           static generateHashFromBlock(block: Block): string
         ```

## Technical requirements

* Create a **clean**, **maintainable** and **well-designed** code. We expect to see a good and clear architecture that
  allows to add or modify the solution without so much troubles.
* **Test** your code until you are comfortable with it. We don't expect a 100% of Code Coverage but some tests that
  helps to have a more stable and confident base code.

To understand how you take decisions during the implementation, **please write a COMMENTS.md** file explaining some of
the most important parts of the application. You would also be able to defend your code through
[Rviewer](https://rviewer.io), once you submit your solution.

---

## How to submit your solution

* Push your code to the `devel` branch - we encourage you to commit regularly to show your thinking process was.
* **Create a new Pull Request** to `main` branch & **merge it**.

Once merged you **won't be able to change or add** anything to your solution, so double-check that everything is as
you expected!

Remember that **there is no countdown**, so take your time and implement a solution that you are proud!

---

<p align="center">
  If you have any feedback or problem, <a href="mailto:help@rviewer.io">let us know!</a> 🤘
  <br><br>
  Made with ❤️ by <a href="https://rviewer.io">Rviewer</a>
</p>
